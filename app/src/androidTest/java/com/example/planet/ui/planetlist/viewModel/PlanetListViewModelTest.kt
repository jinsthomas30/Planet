import com.example.planet.R
import com.example.planet.common.Constants.Companion.MESSAGE_OK
import com.example.planet.resource.ResourceProvider
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.state.DialogState
import com.example.planet.state.LoaderState
import com.example.planet.ui.planetlist.data.PlanetEntity
import com.example.planet.ui.planetlist.data.PlanetResponse
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class PlanetListViewModelTest {

    @Test
    fun isLoading_initially() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial isLoading state
        assertEquals(true, viewModel.isLoading.first().isLoader)
    }

    @Test
    fun getPlanetList_initially() {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial planetList state
        assertEquals(emptyList<PlanetEntity>(), viewModel.planetList.value)
    }

    @Test
    fun getDialogState_initially() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial dialogState state
        assertEquals(DialogState.Hidden, viewModel.dialogState.value)
    }

    @Test
    fun fetchPlanetList_success() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock successful response from API
        val response: Response<PlanetResponse> = mockk()
        every { response.isSuccessful } returns true

        coEvery { myApiRepo.fetchPlanetList() } returns response

        // Call the function under test
        viewModel.fetchPlanetList()

        // Verify interactions
        coEvery { planetDbRepository.getAllPlanet() }
        coEvery { planetDbRepository.insertPlanetList(any()) }
        assertEquals(listOf<PlanetEntity>(), viewModel.planetList.value)
    }

    @Test
    fun fetchPlanetList_networkError() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getAllPlanet() } returns emptyList()

        // Mock network error from API
        coEvery { myApiRepo.fetchPlanetList() } throws IOException()

        // Call the function under test
        viewModel.fetchPlanetList()

        // Verify interactions
        verify { resourceProvider.getString(R.string.network_error) }
    }

    @Test
    fun fetchPlanetList_unknownError() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getAllPlanet() } returns emptyList()

        // Mock unknown error from API
        coEvery { myApiRepo.fetchPlanetList() } throws Exception()

        // Call the function under test
        viewModel.fetchPlanetList()

        // Verify interactions
        verify { resourceProvider.getString(R.string.unknown_error) }
    }

    @Test
    fun fetchPlanetList_APIError() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetListViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock successful response from API
        val response: Response<PlanetResponse> = mockk()
        every { response.isSuccessful } returns false

        coEvery { myApiRepo.fetchPlanetList() } returns response

        // Call the function under test
        viewModel.fetchPlanetList()

        // Verify interactions
        verify { resourceProvider.getString(R.string.api_error) }
    }

}
