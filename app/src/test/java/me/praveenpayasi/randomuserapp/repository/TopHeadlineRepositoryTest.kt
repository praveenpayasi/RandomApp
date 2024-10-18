package me.praveenpayasi.randomuserapp.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.data.model.Coordinates
import me.praveenpayasi.randomuserapp.data.model.Dob
import me.praveenpayasi.randomuserapp.data.model.Id
import me.praveenpayasi.randomuserapp.data.model.Info
import me.praveenpayasi.randomuserapp.data.model.Location
import me.praveenpayasi.randomuserapp.data.model.Login
import me.praveenpayasi.randomuserapp.data.model.Name
import me.praveenpayasi.randomuserapp.data.model.Picture
import me.praveenpayasi.randomuserapp.data.model.RandomUserResponse
import me.praveenpayasi.randomuserapp.data.model.Registered
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.data.model.Street
import me.praveenpayasi.randomuserapp.data.model.Timezone
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class RandomUserRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var randomUserRepository: RandomUserRepository

    @Before
    fun setup() {
        randomUserRepository = RandomUserRepository(networkService)
    }

    @Test
    fun getRandomUsers_success() = runTest {
        val sampleResults = listOf(
            Result(
                gender = "male",
                name = Name(
                    title = "Mr",
                    first = "John",
                    last = "Doe"
                ),
                location = Location(
                    street = Street(
                        number = 123,
                        name = "Main Street"
                    ),
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
                login = Login(
                    uuid = "unique-uuid",
                    username = "johndoe",
                    password = "password123",
                    salt = "salt",
                    md5 = "md5hash",
                    sha1 = "sha1hash",
                    sha256 = "sha256hash"
                ),
                dob = Dob(
                    date = "1990-01-01",
                    age = 30
                ),
                registered = Registered(
                    date = "2010-01-01",
                    age = 10
                ),
                phone = "555-555-5555",
                cell = "555-555-5556",
                id = Id(
                    name = "SSN",
                    value = "123-45-6789"
                ),
                picture = Picture(
                    large = "https://example.com/large.jpg",
                    medium = "https://example.com/medium.jpg",
                    thumbnail = "https://example.com/thumb.jpg"
                ),
                nat = "US"
            )
        )
        val response = RandomUserResponse(
            results = sampleResults,
            info = Info(seed = "seed", results = 1, page = 1, version = "1.0")
        )
        Mockito.`when`(networkService.getRandomUser(anyInt(), anyInt(), anyString()))
            .thenReturn(response)

        randomUserRepository.getRandomUsers(10).test {
            assertEquals(sampleResults, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun getRandomUsers_networkError() = runTest {
        Mockito.`when`(networkService.getRandomUser(anyInt(), anyInt(), anyString())).thenThrow(
            IOException()
        )

        randomUserRepository.getRandomUsers(10).test {
            val error = awaitError()
            assertTrue(error is IOException)
        }
    }

    @Test
    fun getRandomUsers_emptyResponse() = runTest {
        val emptyResponse = RandomUserResponse(results = emptyList(), info = Info(0, 0, "", ""))
        Mockito.`when`(networkService.getRandomUser(anyInt(), anyInt(), anyString()))
            .thenReturn(emptyResponse)

        randomUserRepository.getRandomUsers(10).test {
            assertEquals(emptyList<Result>(), awaitItem())
            awaitComplete()
        }
    }
}