
package com.example.mercadez.api

/**
 * Modelo de produto retornado pela API do Mercadez.
 *
 * Inclui a URL da fotografia real do produto,
 * quando disponível no banco de dados.
 */
data class ProdutoRealApi(
    val id: Int,
    val nomeProduto: String,
    val tags: String? = null,
    val preco: Double,
    val quantidade: Int,
    val mercado: String,
    val criadoEm: String? = null,
    val imagemUrl: String? = null
)
