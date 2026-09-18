package com.example.mercadez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

data class Afiliado(
    val nome: String,
    val drawableId: Int
)

enum class FiltroPreco {
    MAIS_BARATOS,
    MAIS_CAROS
}

data class ProdutoOferta(
    val nome: String,
    val precoBase: Double,
    val drawableId: Int,
    val corFundo: Color
)

sealed class Tela(val rota: String, val titulo: String) {
    object Principal : Tela("principal", "Mercadez")
    object ProdutosDestaque : Tela("produtos_destaque", "Produtos em Destaque")
    object ComparacaoPrecos : Tela("comparacao_precos", "Comparação de Preços")
    object Login : Tela("login", "Login")
    object FaleConosco : Tela("fale_conosco", "Fale Conosco")
    object Ofertas : Tela("ofertas", "Ofertas")
    object Cadastro : Tela("cadastro", "Cadastro")
    object Afiliado : Tela("afiliado", "Cadastro de afiliado")
}

val itensMenu = listOf(
    Tela.Principal,
    Tela.ProdutosDestaque,
    Tela.ComparacaoPrecos,
    Tela.Login,
    Tela.FaleConosco,
    Tela.Ofertas
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MercadezApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MercadezApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route ?: Tela.Principal.rota
    val tituloAtual = itensMenu.firstOrNull { it.rota == rotaAtual }?.titulo
        ?: "Mercadez"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Mercadez",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                itensMenu.forEach { tela ->
                    NavigationDrawerItem(
                        label = { Text(tela.titulo) },
                        selected = rotaAtual == tela.rota,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(tela.rota) {
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(tituloAtual) },
                    navigationIcon = {
                        if (rotaAtual == Tela.Principal.rota) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Abrir menu")
                            }
                        } else {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Tela.Principal.rota,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Tela.Principal.rota) { TelaPrincipal() }
                composable(Tela.ProdutosDestaque.rota) { TelaProdutosDestaque() }
                composable(Tela.ComparacaoPrecos.rota) { TelaComparacaoPrecosMercadez() }
                composable(Tela.Login.rota) {
                    TelaLogin(
                        onCadastroClick = {
                            navController.navigate(Tela.Cadastro.rota)
                        },
                        onAfiliadoClick = {
                            navController.navigate(Tela.Afiliado.rota)
                        }
                    )
                }

                composable(Tela.Cadastro.rota) {
                    TelaCadastro()
                }

                composable(Tela.Afiliado.rota) {
                    TelaAfiliado()
                }
                composable(Tela.FaleConosco.rota) { TelaFaleConosco() }
                composable(Tela.Ofertas.rota) { TelaOfertas() }
            }
        }
    }
}

@Composable
fun TelaPrincipal() {

    var pesquisa by remember { mutableStateOf("") }

    val afiliados = listOf(
        Afiliado(nome = "Mercado Mais", drawableId = R.drawable.mercado_mario),
        Afiliado(nome = "Tá na Cesta", drawableId = R.drawable.mercado_pouco_preco),
        Afiliado(nome = "Mercado do Bode", drawableId = R.drawable.mercado_bode),
        Afiliado(nome = "Banana Feliz", drawableId = R.drawable.banana_feliz)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        // Banner do topo com o mascote
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFA726), Color(0xFFFFD54F))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.precinho_maior),
                contentDescription = "Mascote Mercadez",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = "Mercadez",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "O mercado nota dez!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Busca com botão de lupa à direita
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("Buscar produto...") },
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { /* TODO: ação de busca */ },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFFFA726))
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Seção Afiliados
            Text(
                text = "Afiliados",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            afiliados.chunked(2).forEach { linha ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    linha.forEach { afiliado ->
                        CardAfiliado(
                            afiliado = afiliado,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (linha.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
fun CardAfiliado(
    afiliado: Afiliado,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
    ) {
        Image(
            painter = painterResource(id = afiliado.drawableId),
            contentDescription = afiliado.nome,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(10.dp),
            contentScale = ContentScale.Fit
        )
    }
}