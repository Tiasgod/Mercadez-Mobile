
package com.example.mercadez.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

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
