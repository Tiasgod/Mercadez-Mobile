
package com.example.mercadez

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LaranjaMercadez = Color(0xFFFFA726)
private val FundoMercadez = Color(0xFFE7E7E7)

@Composable
fun TelaFaleConosco() {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }

    var mostrarAviso by remember { mutableStateOf(false) }
    var aviso by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMercadez)
    ) {

        // Banner superior com o mascote
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .background(LaranjaMercadez),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.precinho_maior
                ),
                contentDescription = "Mascote Mercadez",
                modifier = Modifier.size(85.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Conteúdo da tela
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Fale Conosco",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Preencha o formulário e entraremos em contato com você.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Campo nome
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Seu nome")
                },
                singleLine = true,
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo e-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Seu e-mail")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = RoundedCornerShape(9.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo mensagem
            OutlinedTextField(
                value = mensagem,
                onValueChange = { mensagem = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                placeholder = {
                    Text("Mensagem")
                },
                shape = RoundedCornerShape(9.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão enviar
            Button(
                onClick = {
                    aviso = when {
                        nome.isBlank() ->
                            "Por favor, informe seu nome."

                        email.isBlank() ->
                            "Por favor, informe seu e-mail."

                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim()).matches() ->
                            "Informe um e-mail válido."

                        mensagem.isBlank() ->
                            "Por favor, escreva sua mensagem."

                        else ->
                            "A tela está pronta, mas o envio ainda precisa ser conectado ao backend."
                    }

                    mostrarAviso = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(9.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LaranjaMercadez,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Enviar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Contato profissional:",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Text(
                text = "tigo4581@hotmail.com",
                fontSize = 14.sp,
                color = Color(0xFFFF7800),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(15.dp))
        }

        // Faixa inferior laranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .background(LaranjaMercadez)
        )
    }

    // Avisos do formulário
    if (mostrarAviso) {
        AlertDialog(
            onDismissRequest = {
                mostrarAviso = false
            },
            title = {
                Text("Fale Conosco")
            },
            text = {
                Text(aviso)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarAviso = false
                    }
                ) {
                    Text(
                        "OK",
                        color = LaranjaMercadez
                    )
                }
            }
        )
    }
}
