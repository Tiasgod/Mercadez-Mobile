
package com.example.mercadez.api

data class CadastroAfiliadoRequest(
    val nome_proprietario: String,
    val email: String,
    val senha: String,
    val cnpj: String,
    val endereco: String,
    val telefone: String,
    val mercado: String,
    val categoria: String,
    val funcionarios: Int,
    val pagamento: String
)
