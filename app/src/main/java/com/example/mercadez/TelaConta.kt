
package com.example.mercadez

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaConta(
    onSair: () -> Unit
) {
    val context = LocalContext.current

    val preferencias = remember {
        context.getSharedPreferences(
            "mercadez",
            Context.MODE_PRIVATE
        )
    }

    var nome by remember {
        mutableStateOf(
            preferencias.getString("nome", "") ?: ""
        )
    }

    var email by remember {
        mutableStateOf(
            preferencias.getString("email", "") ?: ""
        )
    }

    var mensagem by remember {
        mutableStateOf("")
    }

    var mostrarConfirmacao by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9ED))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        // ÍCONE DO PERFIL
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFE3B8)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil do usuário",
                tint = Color(0xFFE58A19),
                modifier = Modifier.size(65.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Minha conta",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF292929)
        )

        Text(
            text = "Gerencie seus dados pessoais",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        // DADOS PESSOAIS
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Dados pessoais",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF292929)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Nome",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = nome,
                    onValueChange = {
                        nome = it
                        mensagem = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text("Seu nome")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "E-mail",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        mensagem = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text("Seu e-mail")
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (nome.isBlank()) {
                            mensagem = "Informe seu nome."
                        } else if (
                            email.isBlank() ||
                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(email.trim())
                                .matches()
                        ) {
                            mensagem = "Informe um e-mail válido."
                        } else {
                            preferencias.edit()
                                .putString("nome", nome.trim())
                                .putString("email", email.trim())
                                .apply()

                            mensagem = "Dados salvos neste dispositivo."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA539)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Salvar alterações",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (mensagem.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = mensagem,
                        color = if (
                            mensagem.startsWith("Dados salvos")
                        ) {
                            Color(0xFF258B43)
                        } else {
                            Color(0xFFD32F2F)
                        },
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // INFORMAÇÕES DA CONTA
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = "Configurações",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF292929)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFFE58A19)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Column {
                        Text(
                            text = "Conta Mercadez",
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Seus dados e informações pessoais",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SAIR DA CONTA
        Button(
            onClick = {
                mostrarConfirmacao = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFE5E5)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = null,
                tint = Color(0xFFD32F2F)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Sair da conta",
                color = Color(0xFFD32F2F),
                fontWeight = FontWeight.Bold
            )
        }
    }

    // CONFIRMAÇÃO DE SAÍDA
    if (mostrarConfirmacao) {
        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacao = false
            },
            title = {
                Text(
                    text = "Sair da conta?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Deseja realmente sair da sua conta Mercadez?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacao = false
                        onSair()
                    }
                ) {
                    Text(
                        text = "Sair",
                        color = Color(0xFFD32F2F)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacao = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
