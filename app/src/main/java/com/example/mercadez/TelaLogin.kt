
package com.example.mercadez

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mercadez.api.LoginRequest
import com.example.mercadez.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun TelaLogin(
    onCadastroClick: () -> Unit,
    onAfiliadoClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    var carregando by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7E7E7)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFFFA726)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.precinho_maior),
                contentDescription = "Precinho",
                modifier = Modifier.size(100.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 14.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Acesse sua conta Mercadez",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "E-mail",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        mensagem = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Senha",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = {
                        senha = it
                        mensagem = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (mensagem.isNotEmpty()) {
                Text(
                    text = mensagem,
                    color = Color(0xFFD32F2F),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (carregando) Color(0xFFE0A13D)
                        else Color(0xFFFFBA45)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(enabled = !carregando) {

                        when {
                            email.isBlank() -> {
                                mensagem = "Informe seu e-mail."
                            }

                            senha.isBlank() -> {
                                mensagem = "Informe sua senha."
                            }

                            else -> {
                                carregando = true
                                mensagem = ""

                                coroutineScope.launch {
                                    try {
                                        val resposta =
                                            RetrofitClient.api.realizarLogin(
                                                LoginRequest(
                                                    email = email.trim(),
                                                    senha = senha
                                                )
                                            )

                                        if (resposta.isSuccessful) {

                                            val dados = resposta.body()
                                            val token = dados?.token

                                            if (!token.isNullOrBlank()) {

                                                val nomeUsuario =
                                                    dados.nome
                                                        ?.takeIf { it.isNotBlank() }
                                                        ?: email.trim()
                                                            .substringBefore("@")

                                                val emailUsuario =
                                                    dados.email
                                                        ?.takeIf { it.isNotBlank() }
                                                        ?: email.trim()

                                                val preferencias =
                                                    context.getSharedPreferences(
                                                        "mercadez",
                                                        Context.MODE_PRIVATE
                                                    )

                                                preferencias.edit()
                                                    .putString(
                                                        "access_token",
                                                        token
                                                    )
                                                    .putString(
                                                        "nome",
                                                        nomeUsuario
                                                    )
                                                    .putString(
                                                        "email",
                                                        emailUsuario
                                                    )
                                                    .putBoolean(
                                                        "logado",
                                                        true
                                                    )
                                                    .apply()

                                                onLoginSuccess()

                                            } else {
                                                mensagem =
                                                    "A API não retornou o token de acesso."
                                            }

                                        } else {
                                            mensagem = when (resposta.code()) {
                                                401 -> "E-mail ou senha incorretos."
                                                404 -> "Usuário não encontrado."
                                                else -> "Erro ao realizar login. Código: ${resposta.code()}"
                                            }
                                        }

                                    } catch (e: Exception) {
                                        mensagem =
                                            "Não foi possível conectar à API. Verifique sua conexão."
                                    } finally {
                                        carregando = false
                                    }
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {

                if (carregando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Enviar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Ainda não tem cadastro?",
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = "Cadastre-se",
                modifier = Modifier
                    .clickable {
                        onCadastroClick()
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF8C00)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Quer vender seus produtos no Mercadez?",
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = "Cadastre-se como afiliado",
                modifier = Modifier
                    .clickable {
                        onAfiliadoClick()
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF8C00)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(Color(0xFFFFA726))
        )
    }
}
