package com.example.mercadez.api

import com.google.gson.annotations.SerializedName

data class OfertaApi(
    val produto: String,
    @SerializedName("melhor_loja") val melhorLoja: String,
    val preco: Double,
    @SerializedName("preco_mais_alto") val precoMaisAlto: Double,
    val economia: Double,
    @SerializedName("percentual_economia") val percentualEconomia: Double
)
