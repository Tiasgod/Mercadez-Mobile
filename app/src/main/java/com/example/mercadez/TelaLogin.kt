package com.example.mercadez

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.foundation.Image

@Composable
fun TelaLogin(
    onCadastroClick: () -> Unit,
    onAfiliadoClick: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

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

            // E-MAIL

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SENHA

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            // BOTÃO ENVIAR

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFBA45))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        // Futuramente chamar a API de login
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Enviar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // CADASTRO

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

            // CADASTRO DE AFILIADO

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

        // BARRA INFERIOR

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(Color(0xFFFFA726))
        )
    }
}