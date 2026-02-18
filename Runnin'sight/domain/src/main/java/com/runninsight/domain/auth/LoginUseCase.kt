package com.runninsight.domain.auth

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<AuthUser> {
        if (idToken.isBlank()) {
            return Result.failure(IllegalArgumentException("Google idToken must not be blank"))
        }

        return runCatching {
            authRepository.loginWithGoogle(idToken)
        }
    }
}
