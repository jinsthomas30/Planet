import com.example.planet.R
import com.example.planet.resource.ResourceProvider
import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.state.DialogState
import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetdetails.viewModel.PlanetDetailsViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class PlanetDetailsViewModelTest {

    @Test
    fun isLoading_initially() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial isLoading state
        assertEquals(true, viewModel.isLoading.first().isLoader)
    }

    @Test
    fun getPlanetDetails_initially() {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial planetDetails state
        assertEquals(null, viewModel.planetDetails.value)
    }

    @Test
    fun getDialogState_initially() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Test initial dialogState state
        assertEquals(DialogState.Hidden, viewModel.dialogState.value)
    }

    @Test
    fun fetchPlanetDetails_success() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getPlanetDetailsByUid("1") } returns null

        // Mock successful response from API
        val response: Response<PlanetDetailsResponse> = mockk()
        every { response.isSuccessful } returns true

        coEvery { myApiRepo.fetchPlanetDetails("1") } returns response

        // Call the function under test
        viewModel.fetchPlanetDetails("1")

        // Verify interactions
        coEvery { planetDbRepository.getPlanetDetailsByUid("1") }
        coEvery { planetDbRepository.insertPlanetDetailsByUid(any()) }
    }

    @Test
    fun fetchPlanetDetails_network_error() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getPlanetDetailsByUid("1") } returns null

        // Mock network error from API
        coEvery { myApiRepo.fetchPlanetDetails(any()) } throws IOException()

        // Call the function under test
        viewModel.fetchPlanetDetails("uid")

        // Verify interactions
        verify { resourceProvider.getString(R.string.network_error) }
    }

    @Test
    fun fetchPlanetDetails_unknown_error() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getPlanetDetailsByUid("1") } returns null

        // Mock unknown error from API
        coEvery { myApiRepo.fetchPlanetDetails(any()) } throws Exception()

        // Call the function under test
        viewModel.fetchPlanetDetails("1")

        // Verify interactions
        verify { resourceProvider.getString(R.string.unknown_error) }
    }

    @Test
    fun fetchPlanetDetails_API_error() = runTest {
        // Mock dependencies
        val myApiRepo: MyApiRepository = mockk()
        val planetDbRepository: PlanetDbRepository = mockk()
        val resourceProvider: ResourceProvider = mockk()

        // Create the view model with mocked dependencies
        val viewModel = PlanetDetailsViewModel(myApiRepo, planetDbRepository, resourceProvider)

        // Mock database returning empty list
        coEvery { planetDbRepository.getPlanetDetailsByUid("1") } returns null

        // Mock API error response
        val response: Response<PlanetDetailsResponse> = mockk()
        every { response.isSuccessful } returns false

        coEvery { myApiRepo.fetchPlanetDetails("1") } returns response

        // Call the function under test
        viewModel.fetchPlanetDetails("1")

        // Verify interactions
        verify { resourceProvider.getString(R.string.api_error) }
    }
}
