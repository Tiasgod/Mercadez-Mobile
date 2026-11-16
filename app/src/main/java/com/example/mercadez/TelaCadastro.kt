
package com.example.mercadez

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mercadez.api.CadastroRequest
import com.example.mercadez.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun TelaCadastro() {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var mensagem by remember { mutableStateOf("") }
    var sucesso by remember { mutableStateOf(false) }
    var carregando by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7E7E7)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // CABEÇALHO

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

        // CONTEÚDO

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 10.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Cadastro",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Crie sua conta Mercadez",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(28.dp))

            // NOME

            Text(
                text = "Nome de usuário",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    mensagem = ""
                    sucesso = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // E-MAIL

            Text(
                text = "E-mail",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    mensagem = ""
                    sucesso = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // CPF

            Text(
                text = "CPF",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = cpf,
                onValueChange = {
                    cpf = it
                    mensagem = ""
                    sucesso = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // SENHA

            Text(
                text = "Senha",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = senha,
                onValueChange = {
                    senha = it
                    mensagem = ""
                    sucesso = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            // MENSAGEM

            if (mensagem.isNotEmpty()) {

                Text(
                    text = mensagem,
                    fontSize = 13.sp,
                    color = if (sucesso) {
                        Color(0xFF2E7D32)
                    } else {
                        Color(0xFFD32F2F)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            // BOTÃO ENVIAR

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(
                        if (carregando) Color(0xFFE0A13D)
                        else Color(0xFFFFBA45)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(7.dp)
                    )
                    .clickable(enabled = !carregando) {

                        val cpfLimpo = cpf.filter { it.isDigit() }

                        when {

                            nome.isBlank() -> {
                                mensagem = "Informe seu nome."
                                sucesso = false
                            }

                            email.isBlank() -> {
                                mensagem = "Informe seu e-mail."
                                sucesso = false
                            }

                            !email.contains("@") ||
                                    !email.substringAfter("@").contains(".") -> {
                                mensagem = "Informe um e-mail válido."
                                sucesso = false
                            }

                            cpfLimpo.length != 11 -> {
                                mensagem = "Informe um CPF com 11 números."
                                sucesso = false
                            }

                            senha.isBlank() -> {
                                mensagem = "Informe sua senha."
                                sucesso = false
                            }

                            else -> {

                                carregando = true
                                mensagem = ""
                                sucesso = false

                                coroutineScope.launch {

                                    try {

                                        val resposta =
                                            RetrofitClient.api.realizarCadastro(
                                                CadastroRequest(
                                                    nome = nome.trim(),
                                                    email = email.trim(),
                                                    senha = senha,
                                                    cpf = cpfLimpo
                                                )
                                            )

                                        if (resposta.isSuccessful) {

                                            val dados = resposta.body()

                                            if (dados != null) {

                                                mensagem =
                                                    "Cadastro realizado com sucesso!"

                                                sucesso = true

                                                nome = ""
                                                email = ""
                                                cpf = ""
                                                senha = ""

                                            } else {

                                                mensagem =
                                                    "A API não retornou os dados do cadastro."

                                                sucesso = false
                                            }

                                        } else {

                                            mensagem = when (resposta.code()) {

                                                400 -> "Não foi possível realizar o cadastro. Verifique os dados."

                                                409 -> "Este e-mail ou CPF já está cadastrado."

                                                422 -> "Os dados informados são inválidos. Verifique os campos."

                                                else -> "Erro ao realizar cadastro. Código: ${resposta.code()}"
                                            }

                                            sucesso = false
                                        }

                                    } catch (e: Exception) {

                                        mensagem =
                                            "Não foi possível conectar à API. Verifique sua conexão."

                                        sucesso = false

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
                        modifier = Modifier.size(23.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "Enviar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // BARRA INFERIOR

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(37.dp)
                .background(Color(0xFFFFA726)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Início",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )

            Icon(
                imageVector = Icons.Default.Headset,
                contentDescription = "Suporte",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}
