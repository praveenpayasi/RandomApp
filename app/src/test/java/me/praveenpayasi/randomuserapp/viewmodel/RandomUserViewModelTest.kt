package me.praveenpayasi.randomuserapp.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import me.praveenpayasi.randomuserapp.data.model.Coordinates
import me.praveenpayasi.randomuserapp.data.model.Dob
import me.praveenpayasi.randomuserapp.data.model.Id
import me.praveenpayasi.randomuserapp.data.model.Location
import me.praveenpayasi.randomuserapp.data.model.Login
import me.praveenpayasi.randomuserapp.data.model.Name
import me.praveenpayasi.randomuserapp.data.model.Picture
import me.praveenpayasi.randomuserapp.data.model.Registered
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.data.model.Street
import me.praveenpayasi.randomuserapp.data.model.Timezone
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.ui.random.RandomUserViewModel
import me.praveenpayasi.randomuserapp.utils.DispatcherProvider
import me.praveenpayasi.randomuserapp.utils.NetworkHelper
import me.praveenpayasi.randomuserapp.utils.logger.Logger
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RandomUserViewModelTest {

    @Mock
    private lateinit var randomUserRepository: RandomUserRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var logger: Logger

    private lateinit var viewModel: RandomUserViewModel

    @Before
    fun setup() {
        val testDispatcher = TestCoroutineDispatcher()
        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)
        Mockito.`when`(dispatcherProvider.io).thenReturn(testDispatcher)

        viewModel = RandomUserViewModel(
            randomUserRepository, networkHelper, dispatcherProvider, logger
        )
    }

    @Test
    fun onInputChanged_validInput_updatesState() = runTest {
        viewModel.onInputChanged("10")

        assertEquals("10", viewModel.inputText.value)
        assertTrue(viewModel.inputValidationState.value)
    }

    @Test
    fun onInputChanged_invalidInput_updatesState() = runTest {
        viewModel.onInputChanged("abc")

        assertEquals("abc", viewModel.inputText.value)
        assertFalse(viewModel.inputValidationState.value)
    }

    @Test
    fun onInputChanged_invalidInput_clearsUiState() = runTest {
        viewModel.fetchUsers(10) // Populate uiState (mock response if needed)

        viewModel.onInputChanged("invalid")

        //assertTrue(viewModel.uiState.value.empty)
    }

    @Test
    fun fetchUsers_withInternet_fetchesData() = runTest {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(true)

        val sampleResults = listOf(
            Result(
                gender = "male",
                name = Name("Mr", "John", "Doe"),
                location = Location(
                    street = Street("Main Street", 123),
                    city = "New York",
                    state = "NY",
                    country = "USA",
                    postcode = "10001",
                    coordinates = Coordinates(
                        latitude = "345456565465465",
                        longitude = "43543654656565"
                    ),
                    timezone = Timezone(
                        description = "description",
                        offset = "435435436"
                    )
                ),
                email = "john.doe@example.com",
                login = Login("uuid", "johndoe", "password", "salt", "md5", "sha1", "sha256"),
                dob = Dob(30, "1990-01-01"),
                registered = Registered(10, "2010-01-01"),
                phone = "555-555-5555",
                cell = "555-555-5556",
                id = Id("SSN", "123-45-6789"),
                picture = Picture(
                    "https://example.com/large.jpg",
                    "https://example.com/medium.jpg",
                    "https://example.com/thumb.jpg"
                ),
                nat = "US"
            )
        )
        val pagingData = PagingData.from(sampleResults)
        Mockito.`when`(randomUserRepository.getRandomUsers(anyInt()))
            .thenReturn(flowOf(pagingData))

        viewModel.fetchUsers(10)

        assertEquals(pagingData, viewModel.uiState.value)
    }

    @Test
    fun fetchUsers_noInternet_logsMessage() = runTest {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(false)

        viewModel.fetchUsers(10)

        Mockito.verify(logger).d("No Internet", "Pls connect to internet")
    }

    @Test
    fun fetchUsers_noInternet_doesNotFetchData() = runTest {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(false)

        viewModel.fetchUsers(10)

        Mockito.verify(randomUserRepository, Mockito.never()).getRandomUsers(anyInt())
    }
}