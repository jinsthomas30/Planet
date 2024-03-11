package com.example.planet

import com.example.planet.respository.MyApiRepository
import com.example.planet.respository.PlanetDbRepository
import com.example.planet.ui.planetdetails.data.PlanetDetailsResponse
import com.example.planet.ui.planetdetails.data.PlanetDtEntity
import com.example.planet.ui.planetdetails.data.Result
import com.example.planet.ui.planetdetails.viewModel.PlanetDetailsViewModel
import com.example.planet.ui.planetlist.data.PlanetResponse
import com.example.planet.ui.planetlist.viewModel.PlanetListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PlanetDetailsViewModelTest {

    private lateinit var viewModel: PlanetDetailsViewModel
    private lateinit var repository: MyApiRepository
    private lateinit var planetDbRepository: PlanetDbRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = mockk()
        planetDbRepository = mockk()
        viewModel = PlanetDetailsViewModel(repository, planetDbRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun test_fetching_planet_details_successfully() = runBlocking {
        // Given
        val uid = "1"
        val expectedPlanetDetails = PlanetDtEntity("1","arid","2024-03-11T08:38:48.936Z","10465","","1 standard","Tatooine","23","200000","304","1",
            "desert","https://www.swapi.tech/api/planets/1")

        coEvery { repository.planetDetails(uid) } returns Response.success(PlanetDetailsResponse("ok",result = Result(0,"5f7254c11b7dfa00041c6fae","A planet.",expectedPlanetDetails,"1")))

        // When
        viewModel.fetchPlanetDt(uid)

        // Then
        val isLoading = viewModel.isLoading.first()
        val planetDetails = viewModel.planetDetails.first()

        assertEquals(false, isLoading)
        assertEquals(expectedPlanetDetails, planetDetails)

        // Verify that the repository methods were called
        coVerify { repository.planetDetails(uid) }
        coVerify { planetDbRepository.insertPlanetDt(expectedPlanetDetails) }
    }

    @Test
    fun test_fetching_planet_details_with_error() = runBlocking {
      /*  // Given
        val repository = mockk<MyApiRepository>()
        val planetDbRepository = mockk<PlanetDbRepository>()
        val viewModel = PlanetDetailsViewModel(repository, planetDbRepository)
        val uid = "1"

        coEvery { repository.planetDetails(uid) } throws Exception()

        // When
        viewModel.fetchPlanetDt(uid)

        // Then
        val isLoading = viewModel.isLoading.first()
        val planetDetails = viewModel.planetDetails.first()

        assertEquals(false, isLoading)
        assertEquals(PlanetDtEntity("","","","","","","","","","","",
            "",""), planetDetails) // Assuming you have a default empty object

        // Verify that the repository methods were called
        coVerify { repository.planetDetails(uid) }
        coVerify { planetDbRepository.getPlanetDt(uid) }*/
    }
}
