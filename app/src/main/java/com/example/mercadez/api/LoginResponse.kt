
package com.example.mercadez.api

data class LoginResponse(
    val token: String? = null,
    val tipo: String? = null,
    val perfil: String? = null,
    val id: Int? = null,
    val nome: String? = null,
    val email: String? = null
)
