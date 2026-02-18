package com.runninsight.domain.auth

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?
)
