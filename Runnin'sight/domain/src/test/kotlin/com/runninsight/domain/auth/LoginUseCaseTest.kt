package com.runninsight.domain.auth

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class LoginUseCaseTest {

    @Test
    fun `returns failure when idToken is blank`() = runTest {
        val useCase = LoginUseCase(FakeAuthRepository())

        val result = useCase("   ")

        assertTrue(result.isFailure)
        assertIsIllegalArgument(result.exceptionOrNull())
    }

    @Test
    fun `returns user when repository succeeds`() = runTest {
        val expectedUser = AuthUser(uid = "uid-1", email = "runner@runninsight.dev", displayName = "Runner")
        val useCase = LoginUseCase(FakeAuthRepository(user = expectedUser))

        val result = useCase("valid-google-token")

        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
    }

    @Test
    fun `returns failure when repository throws`() = runTest {
        val expectedError = IllegalStateException("Firebase sign-in failed")
        val useCase = LoginUseCase(FakeAuthRepository(error = expectedError))

        val result = useCase("valid-google-token")

        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }

    private fun assertIsIllegalArgument(throwable: Throwable?) {
        assertFailsWith<IllegalArgumentException> { throw throwable ?: error("Expected throwable") }
    }

    private class FakeAuthRepository(
        private val user: AuthUser = AuthUser("default", null, null),
        private val error: Throwable? = null
    ) : AuthRepository {
        override suspend fun loginWithGoogle(idToken: String): AuthUser {
            error?.let { throw it }
            return user
        }
    }
}
