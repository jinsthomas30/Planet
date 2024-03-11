import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetlist.data.PlanetEntity
import com.example.planet.ui.planetlist.data.PlanetResponse
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PlanetListViewModelTest {

    private lateinit var viewModel: PlanetListViewModel
    private lateinit var repository: MyApiRepository
    private lateinit var planetDbRepository: PlanetDbRepository

    // Mock data for testing
    private val planetEntity = PlanetEntity("1", "Tatooine", "https://www.swapi.tech/api/planets/1")
    private val planetResponse = PlanetResponse("ok", "https://www.swapi.tech/api/planets?page=2&limit=10", "", listOf(planetEntity), 1, 60)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mockk()
        planetDbRepository = mockk()

        viewModel = PlanetListViewModel(repository, planetDbRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `test_fetching_planet_list_successfully`() = runBlocking {
        // Given
        coEvery { repository.planetList() } returns Response.success(planetResponse)
        coEvery { planetDbRepository.getAllPlanet() } returns listOf(planetEntity)

        // When
        //Need to make public
        viewModel.fetchPlanetList()

        // Then
        val isLoading = viewModel.isLoading.first()
        val planetList = viewModel.planetList.first()

        assertFalse(isLoading)
        assertEquals(listOf(planetEntity), planetList)

        // Verify that the repository methods were called
        coVerify { repository.planetList() }
        coVerify { planetDbRepository.getAllPlanet() }
    }

    @Test
    fun `test_fetching_planet_list_with_error`() = runBlocking {
        // Given
        coEvery { repository.planetList() } throws Exception()
        coEvery { planetDbRepository.getAllPlanet() } returns emptyList()

        // When
        //Need to make public
        viewModel.fetchPlanetList()

        // Then
        val isLoading = viewModel.isLoading.first()
        val planetList = viewModel.planetList.first()

        assertFalse(isLoading)
        assertTrue(planetList.isEmpty())

        // Verify that the repository methods were called
        coVerify { repository.planetList() }
        coVerify { planetDbRepository.getAllPlanet() }
    }
}
