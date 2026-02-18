package com.runninsight.data.auth

import com.runninsight.domain.auth.AuthRepository
import com.runninsight.domain.auth.AuthUser

class FirebaseAuthRepository(
    private val firebaseAuthApi: FirebaseAuthApi
) : AuthRepository {

    override suspend fun loginWithGoogle(idToken: String): AuthUser {
        val firebaseUser = firebaseAuthApi.signInWithGoogleIdToken(idToken)
        return AuthUser(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
            displayName = firebaseUser.displayName
        )
    }
}