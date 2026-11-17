
package com.example.mercadez.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("produtos")
    suspend fun listarProdutos(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
        @Query("com_imagem") comImagem: Boolean = false
    ): Response<List<ProdutoRealApi>>

    @GET("produtos/buscar")
    suspend fun buscarProdutos(@Query("q") termo: String): Response<List<ProdutoRealApi>>

    @GET("ofertas")
    suspend fun listarOfertas(@Query("limite") limite: Int = 100): Response<List<OfertaApi>>


    // LOGIN
    @POST("usuarios/login")
    suspend fun realizarLogin(
        @Body dados: LoginRequest
    ): Response<LoginResponse>

    // CADASTRO DE CLIENTE
    @POST("usuarios/cadastro")
    suspend fun realizarCadastro(
        @Body dados: CadastroRequest
    ): Response<CadastroResponse>

    // CADASTRO DE AFILIADO
    @POST("afiliados")
    suspend fun realizarCadastroAfiliado(
        @Body dados: CadastroAfiliadoRequest
    ): Response<CadastroAfiliadoResponse>
}
