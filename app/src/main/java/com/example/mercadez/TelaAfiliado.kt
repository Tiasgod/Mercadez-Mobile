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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaAfiliado() {

    var proprietario by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var cnpj by remember {
        mutableStateOf("")
    }

    var endereco by remember {
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    var funcionarios by remember {
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
                    start = 24.dp,
                    end = 24.dp,
                    top = 8.dp
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

            Text(
                text = "Nome do proprietário",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = proprietario,
                onValueChange = {
                    proprietario = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            // CNPJ

            Text(
                text = "CNPJ",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = cnpj,
                onValueChange = {
                    cnpj = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            // ENDEREÇO

            Text(
                text = "Endereço",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = endereco,
                onValueChange = {
                    endereco = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            // TELEFONE

            Text(
                text = "Telefone",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    telefone = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(11.dp))

            // NÚMERO DE FUNCIONÁRIOS

            Text(
                text = "Número de funcionários",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = funcionarios,
                onValueChange = {
                    funcionarios = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                singleLine = true,
                shape = RoundedCornerShape(7.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // BOTÃO ENVIAR

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(Color(0xFFFFBA45))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(7.dp)
                    )
                    .clickable {
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Enviar",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
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