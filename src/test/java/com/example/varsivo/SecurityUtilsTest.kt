package com.example.varsivo

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SecurityUtilsTest {

    @Test
    fun correctPassword_verifiesSuccessfully() {

        val salt = SecurityUtils.generateSalt()

        val hash = SecurityUtils.hashPassword(
            "Password123",
            salt
        )

        assertTrue(
            SecurityUtils.verifyPassword(
                "Password123",
                hash,
                salt
            )
        )
    }

    @Test
    fun incorrectPassword_doesNotVerify() {

        val salt = SecurityUtils.generateSalt()

        val hash = SecurityUtils.hashPassword(
            "Password123",
            salt
        )

        assertFalse(
            SecurityUtils.verifyPassword(
                "WrongPassword",
                hash,
                salt
            )
        )
    }

    @Test
    fun differentSalts_produceDifferentHashes() {

        val salt1 = SecurityUtils.generateSalt()
        val salt2 = SecurityUtils.generateSalt()

        val hash1 = SecurityUtils.hashPassword(
            "Password123",
            salt1
        )

        val hash2 = SecurityUtils.hashPassword(
            "Password123",
            salt2
        )

        assertNotEquals(hash1, hash2)
    }

    @Test
    fun samePasswordAndSalt_produceSameHash() {

        val salt = SecurityUtils.generateSalt()

        val hash1 = SecurityUtils.hashPassword(
            "Password123",
            salt
        )

        val hash2 = SecurityUtils.hashPassword(
            "Password123",
            salt
        )

        assertTrue(hash1 == hash2)
    }
}