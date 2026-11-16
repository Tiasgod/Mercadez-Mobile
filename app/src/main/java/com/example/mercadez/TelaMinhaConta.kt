package com.example.mercadez

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ContaLaranja = Color(0xFFFFA539)
private val ContaAmarelo = Color(0xFFFFC942)
private val ContaFundo = Color(0xFFFFF9ED)
private val ContaTexto = Color(0xFF292929)

@Composable
fun TelaMinhaConta(onSair: () -> Unit) {
    val context = LocalContext.current
    val preferencias = remember {
        context.getSharedPreferences("mercadez", Context.MODE_PRIVATE)
    }

    var nome by remember {
        mutableStateOf(preferencias.getString("nome", "Cliente") ?: "Cliente")
    }
    var email by remember {
        mutableStateOf(preferencias.getString("email", "") ?: "")
    }
    var mostrarEdicao by remember { mutableStateOf(false) }
    var mostrarSaida by remember { mutableStateOf(false) }
    var mostrarAviso by remember { mutableStateOf(false) }
    var tituloAviso by remember { mutableStateOf("") }
    var textoAviso by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ContaFundo)
            .verticalScroll(rememberScrollState())
    ) {
        // CABEÇALHO DA CONTA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(ContaLaranja, ContaAmarelo)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            nome.firstOrNull()?.uppercase() ?: "C",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ContaLaranja
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Olá, ${nome.substringBefore(" ")}!",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            if (email.isBlank()) "Bem-vindo ao Mercadez" else email,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                        Text(
                            "Minha conta",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7A4300),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.85f))
                                .clickable { mostrarEdicao = true }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ImagePrecinhoConta()
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 17.dp, vertical = 18.dp)
        ) {
            ContaTituloSecao("Seus dados")
            ContaOpcao(
                icon = { Icon(Icons.Default.PersonOutline, null) },
                titulo = "Editar perfil",
                descricao = "Atualize seu nome e e-mail"
            ) { mostrarEdicao = true }
            ContaOpcao(
                icon = { Icon(Icons.Default.Lock, null) },
                titulo = "Senha e segurança",
                descricao = "Cuide da segurança da sua conta"
            ) {
                tituloAviso = "Senha e segurança"
                textoAviso = "A alteração de senha ainda não está disponível nesta versão do aplicativo."
                mostrarAviso = true
            }

            Spacer(modifier = Modifier.height(20.dp))
            ContaTituloSecao("Minha experiência")
            ContaOpcao(
                icon = { Icon(Icons.Default.FavoriteBorder, null) },
                titulo = "Favoritos",
                descricao = "Veja os produtos que você salvou"
            ) {
                tituloAviso = "Favoritos"
                textoAviso = "Sua área de favoritos estará disponível em uma próxima atualização."
                mostrarAviso = true
            }
            ContaOpcao(
                icon = { Icon(Icons.Default.ListAlt, null) },
                titulo = "Minhas listas",
                descricao = "Organize suas compras"
            ) {
                tituloAviso = "Minhas listas"
                textoAviso = "A área de listas da conta estará disponível em uma próxima atualização."
                mostrarAviso = true
            }
            ContaOpcao(
                icon = { Icon(Icons.Default.NotificationsNone, null) },
                titulo = "Notificações",
                descricao = "Preferências de avisos e ofertas"
            ) {
                tituloAviso = "Notificações"
                textoAviso = "As preferências de notificações ainda não estão disponíveis."
                mostrarAviso = true
            }

            Spacer(modifier = Modifier.height(20.dp))
            ContaTituloSecao("Suporte e configurações")
            ContaOpcao(
                icon = { Icon(Icons.Default.Settings, null) },
                titulo = "Configurações",
                descricao = "Personalize sua experiência"
            ) {
                tituloAviso = "Configurações"
                textoAviso = "As configurações adicionais estarão disponíveis em breve."
                mostrarAviso = true
            }
            ContaOpcao(
                icon = { Icon(Icons.Default.HelpOutline, null) },
                titulo = "Ajuda e suporte",
                descricao = "Precisa de ajuda? Fale com a gente"
            ) {
                tituloAviso = "Ajuda e suporte"
                textoAviso = "Acesse a opção Fale Conosco pelo menu do aplicativo."
                mostrarAviso = true
            }

            Spacer(modifier = Modifier.height(22.dp))
            Button(
                onClick = { mostrarSaida = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE4E0),
                    contentColor = Color(0xFFC0392B)
                )
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text("Sair da conta", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                "Mercadez • O mercado nota dez!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (mostrarEdicao) {
        AlertDialog(
            onDismissRequest = { mostrarEdicao = false },
            title = { Text("Editar perfil", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        preferencias.edit()
                            .putString("nome", nome)
                            .putString("email", email)
                            .apply()
                        mostrarEdicao = false
                    }
                ) {
                    Text("Salvar", color = ContaLaranja)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarEdicao = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (mostrarSaida) {
        AlertDialog(
            onDismissRequest = { mostrarSaida = false },
            title = { Text("Sair da conta?") },
            text = { Text("Você precisará entrar novamente para acessar sua conta.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarSaida = false
                        onSair()
                    }
                ) {
                    Text("Sair", color = Color(0xFFC0392B))
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarSaida = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (mostrarAviso) {
        AlertDialog(
            onDismissRequest = { mostrarAviso = false },
            title = { Text(tituloAviso, fontWeight = FontWeight.Bold) },
            text = { Text(textoAviso) },
            confirmButton = {
                TextButton(onClick = { mostrarAviso = false }) {
                    Text("Entendi", color = ContaLaranja)
                }
            }
        )
    }
}

@Composable
private fun ImagePrecinhoConta() {
    androidx.compose.foundation.Image(
        painter = painterResource(R.drawable.precinho_maior),
        contentDescription = "Precinho",
        modifier = Modifier.size(66.dp)
    )
}

@Composable
private fun ContaTituloSecao(titulo: String) {
    Text(
        titulo,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        color = ContaTexto,
        modifier = Modifier.padding(bottom = 9.dp)
    )
}

@Composable
private fun ContaOpcao(
    icon: @Composable () -> Unit,
    titulo: String,
    descricao: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(43.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFE8C8)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.runtime.CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides ContaLaranja
                ) {
                    icon()
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ContaTexto
                )
                Text(
                    descricao,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
