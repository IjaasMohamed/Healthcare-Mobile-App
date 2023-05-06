package com.example.appointment

import org.junit.*
import org.junit.Assert.assertEquals

object FetchingUtil {
    fun fetchData(): String {
        return "Data fetched successfully"
    }
}

class FetchingUtilTest {

    fun test_fetchData() {
        val result = FetchingUtil.fetchData()
        assertEquals("Data fetched successfully", result)
    }

}