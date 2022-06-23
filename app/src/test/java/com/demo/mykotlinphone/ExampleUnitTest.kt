package com.demo.mykotlinphone

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun main() =
        runBlocking {
            var launch = launch {
                delay(1000)
                println("launch")
                "尼玛"
            }

            var async = async {
                delay(1000)
                println("async")
                "我特么"
            }
            val temp = launch.join()
            val await = async.await()
            println()

        }
}