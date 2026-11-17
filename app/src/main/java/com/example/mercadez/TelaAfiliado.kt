
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mercadez.api.CadastroAfiliadoRequest
import com.example.mercadez.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun TelaAfiliado() {

    var proprietario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var mercado by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var funcionarios by remember { mutableStateOf("") }
    var pagamento by remember { mutableStateOf("") }

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
                    top = 8.dp,
                    bottom = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Cadastro de afiliado",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Cadastre seu mercado\npara análise",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // NOME DO PROPRIETÁRIO

            CampoAfiliado(
                titulo = "Nome do proprietário",
                valor = proprietario,
                aoAlterar = {
                    proprietario = it
                    mensagem = ""
                }
            )

            // E-MAIL

            CampoAfiliado(
                titulo = "E-mail",
                valor = email,
                aoAlterar = {
                    email = it
                    mensagem = ""
                }
            )

            // SENHA

            CampoAfiliado(
                titulo = "Senha",
                valor = senha,
                aoAlterar = {
                    senha = it
                    mensagem = ""
                },
                senha = true
            )

            // CNPJ

            CampoAfiliado(
                titulo = "CNPJ",
                valor = cnpj,
                aoAlterar = {
                    cnpj = it
                    mensagem = ""
                }
            )

            // ENDEREÇO

            CampoAfiliado(
                titulo = "Endereço",
                valor = endereco,
                aoAlterar = {
                    endereco = it
                    mensagem = ""
                }
            )

            // TELEFONE

            CampoAfiliado(
                titulo = "Telefone",
                valor = telefone,
                aoAlterar = {
                    telefone = it
                    mensagem = ""
                }
            )

            // NOME DO MERCADO

            CampoAfiliado(
                titulo = "Nome do mercado",
                valor = mercado,
                aoAlterar = {
                    mercado = it
                    mensagem = ""
                }
            )

            // CATEGORIA

            CampoAfiliado(
                titulo = "Categoria",
                valor = categoria,
                aoAlterar = {
                    categoria = it
                    mensagem = ""
                }
            )

            // FUNCIONÁRIOS

            CampoAfiliado(
                titulo = "Número de funcionários",
                valor = funcionarios,
                aoAlterar = {
                    funcionarios = it.filter { caractere ->
                        caractere.isDigit()
                    }
                    mensagem = ""
                }
            )

            // PAGAMENTO

            CampoAfiliado(
                titulo = "Forma de pagamento",
                valor = pagamento,
                aoAlterar = {
                    pagamento = it
                    mensagem = ""
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // MENSAGEM

            if (mensagem.isNotEmpty()) {

                Text(
                    text = mensagem,
                    fontSize = 13.sp,
                    color = if (sucesso) {
                        Color(0xFF2E7D32)
                    } else {
                        Color(0xFFD32F2F)
                    },
                    textAlign = TextAlign.Center
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

                        val cnpjLimpo = cnpj.filter { it.isDigit() }
                        val numeroFuncionarios = funcionarios.toIntOrNull()

                        when {

                            proprietario.isBlank() -> {
                                mensagem = "Informe o nome do proprietário."
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

                            senha.isBlank() -> {
                                mensagem = "Informe uma senha."
                                sucesso = false
                            }

                            cnpjLimpo.length != 14 -> {
                                mensagem = "Informe um CNPJ com 14 números."
                                sucesso = false
                            }

                            endereco.isBlank() -> {
                                mensagem = "Informe o endereço do mercado."
                                sucesso = false
                            }

                            telefone.isBlank() -> {
                                mensagem = "Informe o telefone."
                                sucesso = false
                            }

                            mercado.isBlank() -> {
                                mensagem = "Informe o nome do mercado."
                                sucesso = false
                            }

                            categoria.isBlank() -> {
                                mensagem = "Informe a categoria."
                                sucesso = false
                            }

                            numeroFuncionarios == null -> {
                                mensagem = "Informe o número de funcionários."
                                sucesso = false
                            }

                            pagamento.isBlank() -> {
                                mensagem = "Informe a forma de pagamento."
                                sucesso = false
                            }

                            else -> {

                                carregando = true
                                mensagem = ""
                                sucesso = false

                                coroutineScope.launch {

                                    try {

                                        val resposta =
                                            RetrofitClient.api.realizarCadastroAfiliado(
                                                CadastroAfiliadoRequest(
                                                    nome_proprietario = proprietario.trim(),
                                                    email = email.trim(),
                                                    senha = senha,
                                                    cnpj = cnpjLimpo,
                                                    endereco = endereco.trim(),
                                                    telefone = telefone.trim(),
                                                    mercado = mercado.trim(),
                                                    categoria = categoria.trim(),
                                                    funcionarios = numeroFuncionarios,
                                                    pagamento = pagamento.trim()
                                                )
                                            )

                                        if (resposta.isSuccessful) {

                                            val dados = resposta.body()

                                            if (dados != null) {

                                                mensagem =
                                                    "Cadastro de afiliado realizado com sucesso!"

                                                sucesso = true

                                                proprietario = ""
                                                email = ""
                                                senha = ""
                                                cnpj = ""
                                                endereco = ""
                                                telefone = ""
                                                mercado = ""
                                                categoria = ""
                                                funcionarios = ""
                                                pagamento = ""

                                            } else {

                                                mensagem =
                                                    "A API não retornou os dados do cadastro."

                                                sucesso = false
                                            }

                                        } else {

                                            mensagem = when (resposta.code()) {

                                                400 -> "Não foi possível realizar o cadastro. Verifique os dados."

                                                409 -> "Este e-mail ou CNPJ já está cadastrado."

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

            Spacer(modifier = Modifier.height(12.dp))
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

// CAMPO REUTILIZÁVEL

@Composable
private fun CampoAfiliado(
    titulo: String,
    valor: String,
    aoAlterar: (String) -> Unit,
    senha: Boolean = false
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = titulo,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = aoAlterar,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            singleLine = true,
            visualTransformation = if (senha) {
                PasswordVisualTransformation()
            } else {
                androidx.compose.ui.text.input.VisualTransformation.None
            },
            shape = RoundedCornerShape(7.dp)
        )

        Spacer(modifier = Modifier.height(11.dp))
    }
}
