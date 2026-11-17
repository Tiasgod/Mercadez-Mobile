
package com.example.mercadez.api

data class CadastroAfiliadoResponse(
    val id: Int? = null,
    val nome_proprietario: String? = null,
    val email: String? = null,
    val cnpj: String? = null,
    val endereco: String? = null,
    val telefone: String? = null,
    val mercado: String? = null,
    val categoria: String? = null,
    val funcionarios: Int? = null,
    val pagamento: String? = null,
    val ativo: Boolean? = null,
    val criadoEm: String? = null
)
