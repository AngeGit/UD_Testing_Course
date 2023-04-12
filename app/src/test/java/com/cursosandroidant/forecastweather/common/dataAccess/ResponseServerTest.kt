package com.cursosandroidant.forecastweather.common.dataAccess

import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class ResponseServerTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer= MockWebServer()
        mockWebServer.start()
    }
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun readJsonFileSuccess(){
        val reader= JSONFileLoader().loadJsonString("src/test/resources/response_success")
        assertThat(reader,`is`(notNullValue()))
    }
}