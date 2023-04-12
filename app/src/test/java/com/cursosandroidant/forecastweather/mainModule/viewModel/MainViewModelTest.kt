package com.cursosandroidant.forecastweather.mainModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cursosandroidant.forecastweather.MainCoroutineRule
import com.cursosandroidant.forecastweather.common.dataAccess.WeatherForecastService
import com.cursosandroidant.forecastweather.entities.WeatherForecastEntity
import com.cursosandroidant.forecastweather.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModelTest{
        @get:Rule
        val instanceExecuter= InstantTaskExecutorRule()

        @get:Rule
        val mainCoroutineRule=MainCoroutineRule()

        private lateinit var mainVm:MainViewModel
        private lateinit var service:WeatherForecastService

        companion object{
            private lateinit var retrofit: Retrofit
            @BeforeClass
            @JvmStatic
            fun setupCommon(){
                retrofit=Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

        @Before
        fun setup(){
            mainVm= MainViewModel()
            service= retrofit.create(WeatherForecastService::class.java)
        }

        @Test
        fun checkCurrentWeatherIsNotNullTest() {
            runBlocking {
                val result = service.getWeatherForecastByCoordinates(19.4342, -99.1962, "6364546cb00c113bff0065ac8aea2438","metric", "en")
                assertThat(result.current, `is`(notNullValue())) //Da error404 no access
            }

        }
        @Test
        fun checkTimezoneReturnsMexicoCityTest() {
            runBlocking {
                val result = service.getWeatherForecastByCoordinates(19.4342, -99.1962,"6364546cb00c113bff0065ac8aea2438","metric", "en")
                assertThat(result.timezone, `is`("America/Mexico_City")) //Da error401 Unauthorized
            }

        }
        @Test
        fun checkErrorResponseWithOnlyCoordinatesTest() {
            runBlocking {
                try{
                    service.getWeatherForecastByCoordinates(19.4342, -99.1962, "","metric", "en")
                }catch (e:Exception){
                    assertThat(e.localizedMessage,`is`("HTTP 401 Unauthorized"))
                }
            }

        }
        @Test
        fun checkHourlySizeTest(){
            runBlocking {
                mainVm.getWeatherAndForecast(19.4342, -99.1962,
                    "6364546cb00c113bff0065ac8aea2438", "metric", "en")
                val result=mainVm.getResult().getOrAwaitValue ()
                assertThat(result?.hourly?.size, `is`(48))
            /*            val observer= Observer<WeatherForecastEntity> { }
                try{
                mainVm.getWeatherAndForecast(19.4342, -99.1962,
                    "6364546cb00c113bff0065ac8aea2438", "metric", "en")
                val result=mainVm.getResult().value
                assertThat(result?.hourly?.size, `is`(48))
                }finally{
                    mainVm.getResult().removeObserver(observer)
                }*/
            }
        }



}