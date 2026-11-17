
package com.example.mercadez.api

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String
)
