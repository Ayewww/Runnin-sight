package com.runninsight.data.auth

/**
 * Thin abstractions mirroring Firebase Auth calls.
 * In Android production code, these can be backed by FirebaseAuth + GoogleAuthProvider.
 */

interface FirebaseAuthApi {
    suspend fun signInWithGoogleIdToken(idToken: String): FirebaseUserDto
}

data class FirebaseUserDto(
    val uid: String,
    val email: String?,
    val displayName: String?
)