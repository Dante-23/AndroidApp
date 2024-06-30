package com.example.authapp.learnTests

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class ParameterizedHelperTest(val inp: Int, val expVal: Boolean) {

    @Test
    fun test_isEven() {
        val helper = Helper();
        val res = helper.isEven(inp)
        assertEquals(expVal, res)
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(1, false),
                arrayOf(2, true),
                arrayOf(3, false),
                arrayOf(4, true),
                arrayOf(5, false),
                arrayOf(6, true)
            )
        }
    }

}