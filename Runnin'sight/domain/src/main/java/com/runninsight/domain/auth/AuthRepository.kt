package com.runninsight.domain.auth

/**
 * Domain contract for authentication.
 * Domain knows only this interface and no framework implementation details.
 */
interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): AuthUser
}
