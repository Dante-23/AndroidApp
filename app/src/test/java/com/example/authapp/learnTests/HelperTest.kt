package com.example.authapp.learnTests

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class HelperTest {

    lateinit var helper: Helper

    @Before
    fun setUp() {
        helper = Helper()
        println("setUp")
    }

    @After
    fun tearDown() {
        println("tearDown")
    }

    @Test
    fun test_isEven() {
        assertEquals(true, helper.isEven(10))
    }

    @Test
    fun test_isEven1() {
        assertEquals(false, helper.isEven(11))
    }
}