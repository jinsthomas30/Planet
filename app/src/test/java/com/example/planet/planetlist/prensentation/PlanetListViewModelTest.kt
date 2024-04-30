import com.example.planet.R
import com.example.planet.common.data.DialogState
import com.example.planet.planetlist.data.PlanetEntity
import com.example.planet.planetlist.data.PlanetResponse
import com.example.planet.planetlist.domain.usecase.GetPlanetListLocalUseCase
import com.example.planet.planetlist.domain.usecase.GetPlanetListRemoteUsecase
import com.example.planet.planetlist.domain.usecase.InsertPlanetListUseCase
import com.example.planet.planetlist.prensentation.PlanetListViewModel
import com.example.planet.utils.ResourceProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class PlanetListViewModelTest {

    private lateinit var viewModel: PlanetListViewModel

    @Before
    fun setUp() {

        val mockGetRemoteUseCase: GetPlanetListRemoteUsecase = mockk()
        val mockInsertUseCase: InsertPlanetListUseCase = mockk()
        val mockGetLocalUseCase: GetPlanetListLocalUseCase = mockk()
        val mockResourceProvider: ResourceProvider = mockk()
        every { mockResourceProvider.getString(R.string.network_error) } returns "Network error occurred. Please check your internet connection and try again."
        every { mockResourceProvider.getString(R.string.ok) } returns "OK"
        every { mockResourceProvider.getString(R.string.api_error) } returns "Error occurred while processing the request. Please try again later."

        viewModel = PlanetListViewModel(
            mockGetRemoteUseCase,
            mockInsertUseCase,
            mockGetLocalUseCase,
            mockResourceProvider
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun isLoading() {
        // Verify initial loading state
        assert(viewModel.isLoading.value.isLoader)

        // Update loading state
        viewModel.dismissLoader()

        // Verify loading state after dismissal
        assertEquals(false, viewModel.isLoading.value.isLoader)
    }

    @Test
    fun getPlanetList() {
        // Initially, the planet list should be empty
        assert(viewModel.planetList.value.isEmpty())

        // Update the planet list
        viewModel.planetList.value = listOf(PlanetEntity("1", "Earth", "www.earth/earth.jpg"))

        // Verify the updated planet list
        assertEquals(1, viewModel.planetList.value.size)
    }

    @Test
    fun getDialogState() {
        // Initially, the dialog state should be hidden
        assert(viewModel.dialogState.value == DialogState.Hidden)

        // Show dialog
        viewModel.showDialog("Test message", "Test button")

        // Verify dialog state after showing
        assert(viewModel.dialogState.value is DialogState.Show)

        // Dismiss dialog
        viewModel.dismissDialog()

        // Verify dialog state after dismissal
        assertEquals(DialogState.Hidden, viewModel.dialogState.value)
    }

    @Test
    fun fetchPlanetList_success() = runTest {
        // Mocking the response and objects
        val planetListResponse: Response<PlanetResponse> = mockk()
        val mockPlanets: List<PlanetEntity> =
            listOf(PlanetEntity("1", "Earth", "www.earth/earth.jpg"))
        val mockPlanetResponse =
            PlanetResponse("ok", "nextTestData", "PreviousTestData", mockPlanets, 1, 10)

        // Mocking the behavior of functions
        coEvery { planetListResponse.isSuccessful } returns true
        coEvery { viewModel.getPlanetListLocalUseCase(any()) } returns emptyList()
        coEvery { viewModel.insertPlanetListUseCase(any()) } returns listOf()
        coEvery { viewModel.getPlanetListRemoteUsecase(any()) } returns Response.success(
            mockPlanetResponse
        )

        // Calling the function to be tested
        viewModel.fetchPlanetList()

        // Verifying that remote use case is called
        coVerify { viewModel.getPlanetListRemoteUsecase(any()) }

        runBlocking {
            // Verifying planet list state after successful fetch
            assertEquals(mockPlanets, viewModel.planetList.value)
            // Verifying insertion use case is called
            coVerify { viewModel.insertPlanetListUseCase(any()) }
        }


    }

    @Test
    fun fetchPlanetList_error() = runTest {
        coEvery { viewModel.getPlanetListLocalUseCase(any()) } returns emptyList()
        coEvery { viewModel.getPlanetListRemoteUsecase(any()) } throws IOException()

        viewModel.fetchPlanetList()

        // Verify that remote use case is called
        coVerify { viewModel.getPlanetListRemoteUsecase(any()) }

        advanceTimeBy(3000)

        // Verify error handling
        assertEquals(
            DialogState.Show(
                "Network error occurred. Please check your internet connection and try again.",
                "OK"
            ), viewModel.dialogState.value
        )
    }


    // Test dismissDialog() similarly
}
