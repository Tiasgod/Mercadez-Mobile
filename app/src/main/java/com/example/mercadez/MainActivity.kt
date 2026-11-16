
package com.example.mercadez

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

private val LaranjaMercadez = Color(0xFFFFA539)
private val AmareloMercadez = Color(0xFFFFC942)
private val FundoMercadez = Color(0xFFFFF9ED)
private val TextoMercadez = Color(0xFF292929)
private val VerdeMercadez = Color(0xFF258B43)

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
    object DetalheProduto : Tela("detalhe_produto/{produtoId}", "Detalhes do Produto")
    object ComparacaoPrecos : Tela("comparacao_precos", "Comparação de Preços")
    object Login : Tela("login", "Login")
    object Conta : Tela("conta", "Minha conta")
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

    val context = LocalContext.current

    val preferencias = remember {
        context.getSharedPreferences("mercadez", Context.MODE_PRIVATE)
    }

    var usuarioLogado by remember {
        mutableStateOf(
            preferencias.getBoolean("logado", false) &&
                    !preferencias.getString("access_token", null).isNullOrBlank()
        )
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route ?: Tela.Principal.rota

    val tituloAtual = when (rotaAtual) {
        Tela.Conta.rota -> Tela.Conta.titulo
        Tela.Login.rota -> Tela.Login.titulo
        Tela.Cadastro.rota -> Tela.Cadastro.titulo
        Tela.Afiliado.rota -> Tela.Afiliado.titulo
        Tela.FaleConosco.rota -> Tela.FaleConosco.titulo
        Tela.Ofertas.rota -> Tela.Ofertas.titulo
        else -> itensMenu.firstOrNull {
            it.rota == rotaAtual
        }?.titulo ?: "Mercadez"
    }

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
                        label = {
                            Text(tela.titulo)
                        },
                        selected = rotaAtual == tela.rota,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }

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
                    title = {
                        Text(tituloAtual)
                    },
                    navigationIcon = {

                        if (rotaAtual == Tela.Principal.rota) {

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Abrir menu"
                                )
                            }

                        } else {

                            IconButton(
                                onClick = {
                                    if (!navController.popBackStack()) {
                                        navController.navigate(Tela.Principal.rota) {
                                            popUpTo(Tela.Principal.rota) {
                                                inclusive = false
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar"
                                )
                            }
                        }
                    },
                    actions = {

                        IconButton(
                            onClick = {
                                if (usuarioLogado) {
                                    navController.navigate(Tela.Conta.rota) {
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(Tela.Login.rota) {
                                        launchSingleTop = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFE3B8))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Minha conta",
                                tint = Color(0xFFE58A19),
                                modifier = Modifier.size(25.dp)
                            )
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

                // TELA PRINCIPAL
                composable(Tela.Principal.rota) {

                    TelaPrincipal(
                        onProdutosClick = {
                            navController.navigate(Tela.ProdutosDestaque.rota)
                        },
                        onCompararClick = {
                            navController.navigate(Tela.ComparacaoPrecos.rota)
                        },
                        onProdutoClick = { produtoId ->
                            navController.navigate(
                                Tela.DetalheProduto.rota.replace(
                                    "{produtoId}",
                                    produtoId
                                )
                            )
                        },
                        onContaClick = {
                            if (usuarioLogado) {
                                navController.navigate(Tela.Conta.rota) {
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigate(Tela.Login.rota) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        onOfertasClick = {
                            navController.navigate(Tela.Ofertas.rota) {
                                launchSingleTop = true
                            }
                        },
                        onFaleConoscoClick = {
                            navController.navigate(Tela.FaleConosco.rota) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // PRODUTOS EM DESTAQUE
                composable(Tela.ProdutosDestaque.rota) {

                    TelaProdutosDestaque(
                        onProdutoClick = { produto ->

                            val produtoId = if (
                                produto.nome.contains(
                                    "Feijão",
                                    ignoreCase = true
                                )
                            ) {
                                "feijao"
                            } else {
                                "arroz"
                            }

                            navController.navigate(
                                Tela.DetalheProduto.rota.replace(
                                    "{produtoId}",
                                    produtoId
                                )
                            )
                        }
                    )
                }

                // DETALHES DO PRODUTO
                composable(
                    route = Tela.DetalheProduto.rota,
                    arguments = listOf(
                        navArgument("produtoId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->

                    val produtoId = backStackEntry.arguments
                        ?.getString("produtoId") ?: "arroz"

                    TelaDetalheProduto(
                        produtoId = produtoId
                    )
                }

                // COMPARAÇÃO DE PREÇOS
                composable(Tela.ComparacaoPrecos.rota) {
                    TelaComparacaoPrecosMercadez()
                }

                // LOGIN
                composable(Tela.Login.rota) {

                    TelaLogin(
                        onCadastroClick = {
                            navController.navigate(Tela.Cadastro.rota)
                        },
                        onAfiliadoClick = {
                            navController.navigate(Tela.Afiliado.rota)
                        },
                        onLoginSuccess = {

                            usuarioLogado = true

                            navController.navigate(Tela.Principal.rota) {
                                popUpTo(Tela.Login.rota) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // MINHA CONTA
                composable(Tela.Conta.rota) {

                    TelaMinhaConta(
                        onSair = {

                            preferencias.edit()
                                .remove("access_token")
                                .remove("nome")
                                .remove("email")
                                .putBoolean("logado", false)
                                .apply()

                            usuarioLogado = false

                            navController.navigate(Tela.Login.rota) {
                                popUpTo(Tela.Principal.rota) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // CADASTRO
                composable(Tela.Cadastro.rota) {
                    TelaCadastro()
                }

                // CADASTRO DE AFILIADO
                composable(Tela.Afiliado.rota) {
                    TelaAfiliado()
                }

                // FALE CONOSCO
                composable(Tela.FaleConosco.rota) {
                    TelaFaleConosco()
                }

                // OFERTAS
                composable(Tela.Ofertas.rota) {
                    TelaOfertas()
                }
            }
        }
    }
}

// =====================================================
// TELA PRINCIPAL
// =====================================================

@Composable
fun TelaPrincipal(
    onProdutosClick: () -> Unit,
    onCompararClick: () -> Unit,
    onProdutoClick: (String) -> Unit,
    onContaClick: () -> Unit,
    onOfertasClick: () -> Unit,
    onFaleConoscoClick: () -> Unit
) {

    var pesquisa by remember {
        mutableStateOf("")
    }

    var mostrarLista by remember {
        mutableStateOf(false)
    }

    val produtosLista = remember {
        mutableStateListOf<String>()
    }

    val produtosPopulares = listOf(
        ProdutoOferta(
            nome = "Arroz Camil 5kg",
            precoBase = 23.59,
            drawableId = R.drawable.arroz,
            corFundo = Color(0xFFFFE8D0)
        ),
        ProdutoOferta(
            nome = "Feijão Nota 10 1kg",
            precoBase = 5.89,
            drawableId = R.drawable.feijao,
            corFundo = Color(0xFFFFE8D0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMercadez)
    ) {

        // CONTEÚDO COM ROLAGEM
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            // BUSCA
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = {
                        pesquisa = it
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = {
                        Text("Buscar no Mercadez")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Pesquisar"
                        )
                    },
                    shape = RoundedCornerShape(15.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onProdutosClick,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFFFE3B8))
                ) {

                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Ver produtos",
                        tint = Color(0xFFE58A19)
                    )
                }
            }

            // BANNER PRINCIPAL
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(190.dp)
                    .clickable {
                        onOfertasClick()
                    },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LaranjaMercadez
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF8C19),
                                    Color(0xFFFFC542)
                                )
                            )
                        )
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1.2f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "OFERTAS DA SEMANA",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Economize nas suas compras!",
                            color = Color.White,
                            fontSize = 22.sp,
                            lineHeight = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color.White)
                                .clickable {
                                    onOfertasClick()
                                }
                                .padding(
                                    horizontal = 15.dp,
                                    vertical = 9.dp
                                )
                        ) {

                            Text(
                                text = "Ver ofertas  ›",
                                color = LaranjaMercadez,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Image(
                        painter = painterResource(
                            id = R.drawable.precinho_maior
                        ),
                        contentDescription = "Precinho",
                        modifier = Modifier
                            .weight(0.8f)
                            .height(155.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // CATEGORIAS
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 15.dp
                    )
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        AtalhoCategoria(
                            simbolo = "R$",
                            titulo = "Comparar",
                            onClick = onCompararClick
                        )

                        AtalhoCategoria(
                            simbolo = "★",
                            titulo = "Destaques",
                            onClick = onProdutosClick
                        )

                        AtalhoCategoria(
                            simbolo = "🏪",
                            titulo = "Mercados",
                            onClick = onOfertasClick
                        )

                        AtalhoCategoria(
                            simbolo = "%",
                            titulo = "Ofertas",
                            onClick = onOfertasClick
                        )

                        AtalhoCategoria(
                            simbolo = "☰",
                            titulo = "Categorias",
                            onClick = onProdutosClick
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        AtalhoCategoria(
                            simbolo = "🎟",
                            titulo = "Cupons",
                            onClick = onOfertasClick
                        )

                        AtalhoCategoria(
                            simbolo = "🚚",
                            titulo = "Entrega",
                            onClick = onOfertasClick
                        )

                        AtalhoCategoria(
                            simbolo = "♡",
                            titulo = "Favoritos",
                            onClick = onProdutosClick
                        )

                        AtalhoCategoria(
                            simbolo = "🛒",
                            titulo = "Minha lista",
                            onClick = {
                                mostrarLista = true
                            }
                        )

                        AtalhoCategoria(
                            simbolo = "＋",
                            titulo = "Ver mais",
                            onClick = onCompararClick
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // BANNERS PROMOCIONAIS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                BannerPromocional(
                    titulo = "Ofertas relâmpago",
                    descricao = "Preços especiais!",
                    imagemId = R.drawable.ofertas_relampago_mercadez,
                    cor = Color(0xFFE94B24),
                    modifier = Modifier.weight(1f),
                    onClick = onOfertasClick
                )

                BannerPromocional(
                    titulo = "Compare preços",
                    descricao = "Encontre economia",
                    imagemId = R.drawable.comparar_precos_mercadez,
                    cor = Color(0xFF3695D0),
                    modifier = Modifier.weight(1f),
                    onClick = onCompararClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // OFERTAS EM DESTAQUE
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Ofertas em destaque",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE34B2D),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Ver mais ›",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = LaranjaMercadez,
                    modifier = Modifier.clickable {
                        onOfertasClick()
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                produtosPopulares.forEach { produto ->

                    CardProdutoPopular(
                        produto = produto,
                        modifier = Modifier.weight(1f),
                        onClick = {

                            val produtoId = if (
                                produto.nome.contains(
                                    "Feijão",
                                    ignoreCase = true
                                )
                            ) {
                                "feijao"
                            } else {
                                "arroz"
                            }

                            onProdutoClick(produtoId)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // COMPARAÇÃO DE PREÇOS
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onCompararClick()
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE9C9)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Seu dinheiro vale mais!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextoMercadez
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Compare os preços dos mercados e descubra onde economizar.",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Comparar agora  ›",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE58A19)
                        )
                    }

                    Image(
                        painter = painterResource(
                            id = R.drawable.precinho_maior
                        ),
                        contentDescription = "Precinho",
                        modifier = Modifier.size(85.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // MERCADOS PARCEIROS
            Text(
                text = "Mercados parceiros",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextoMercadez,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Conheça os estabelecimentos participantes",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            val afiliados = listOf(
                Afiliado(
                    "Mercado Mais",
                    R.drawable.mercado_mario
                ),
                Afiliado(
                    "Tá na Cesta",
                    R.drawable.mercado_pouco_preco
                ),
                Afiliado(
                    "Mercado do Bode",
                    R.drawable.mercado_bode
                ),
                Afiliado(
                    "Banana Feliz",
                    R.drawable.banana_feliz
                )
            )

            afiliados.chunked(2).forEach { linha ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    linha.forEach { afiliado ->

                        CardAfiliado(
                            afiliado = afiliado,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (linha.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // FALE CONOSCO E PARCERIAS
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onFaleConoscoClick()
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE9C9)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {

                    Text(
                        text = "Tem dúvidas ou quer fazer uma parceria?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoMercadez
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Estamos aqui para ajudar! Entre em contato com a equipe Mercadez para tirar suas dúvidas ou conversar sobre uma parceria.",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(LaranjaMercadez)
                            .clickable {
                                onFaleConoscoClick()
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Fale conosco",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // MENU INFERIOR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ItemMenuInferior(
                simbolo = "⌂",
                titulo = "Início",
                selecionado = true,
                onClick = {}
            )

            ItemMenuInferior(
                simbolo = "▦",
                titulo = "Produtos",
                onClick = onProdutosClick
            )

            ItemMenuInferior(
                simbolo = "R$",
                titulo = "Comparar",
                onClick = onCompararClick
            )

            ItemMenuInferior(
                simbolo = "%",
                titulo = "Ofertas",
                onClick = onOfertasClick
            )

            ItemMenuInferior(
                simbolo = "♙",
                titulo = "Minha conta",
                onClick = onContaClick
            )
        }
    }

    // JANELA DA MINHA LISTA
    if (mostrarLista) {

        AlertDialog(
            onDismissRequest = {
                mostrarLista = false
            },
            title = {
                Text(
                    text = "Minha lista de compras",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {

                Column {

                    Text(
                        text = "Selecione os produtos para sua lista.",
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    produtosPopulares.forEach { produto ->

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = produtosLista.contains(produto.nome),
                                onCheckedChange = { marcado ->

                                    if (marcado) {
                                        produtosLista.add(produto.nome)
                                    } else {
                                        produtosLista.remove(produto.nome)
                                    }
                                }
                            )

                            Text(
                                text = produto.nome,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Text(
                        text = "${produtosLista.size} produto(s) selecionado(s)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeMercadez
                    )
                }
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        mostrarLista = false
                    }
                ) {
                    Text("Concluir")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        produtosLista.clear()
                    }
                ) {
                    Text("Limpar lista")
                }
            }
        )
    }
}

// =====================================================
// ATALHO DE CATEGORIA
// =====================================================

@Composable
fun AtalhoCategoria(
    simbolo: String,
    titulo: String,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(62.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFFFF3E2)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = simbolo,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE58A19)
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = titulo,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            color = TextoMercadez,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

// =====================================================
// BANNER PROMOCIONAL
// =====================================================

@Composable
fun BannerPromocional(
    titulo: String,
    descricao: String,
    imagemId: Int,
    cor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(125.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = cor
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = imagemId),
                contentDescription = titulo,
                modifier = Modifier.size(35.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = titulo,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = descricao,
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}

// =====================================================
// ITEM DO MENU INFERIOR
// =====================================================

@Composable
fun ItemMenuInferior(
    simbolo: String,
    titulo: String,
    selecionado: Boolean = false,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = simbolo,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = if (selecionado) {
                LaranjaMercadez
            } else {
                Color.DarkGray
            }
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = titulo,
            fontSize = 10.sp,
            fontWeight = if (selecionado) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selecionado) {
                LaranjaMercadez
            } else {
                Color.DarkGray
            }
        )
    }
}

// =====================================================
// BANNER DE OFERTAS
// =====================================================

@Composable
fun BannerOfertas(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = LaranjaMercadez
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "OFERTAS DA SEMANA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Economize nas suas compras!",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = "Confira os produtos em destaque.",
                    fontSize = 12.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        )
                ) {

                    Text(
                        text = "Ver ofertas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = LaranjaMercadez
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(
                    id = R.drawable.precinho_maior
                ),
                contentDescription = "Precinho",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// =====================================================
// ATALHOS RÁPIDOS
// =====================================================

@Composable
fun AtalhoRapido(
    simbolo: String,
    titulo: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(112.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFE3B8)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = simbolo,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFB96B0A)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextoMercadez,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

// =====================================================
// CARD DE PRODUTO POPULAR
// =====================================================

@Composable
fun CardProdutoPopular(
    produto: ProdutoOferta,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(produto.corFundo),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(
                        id = produto.drawableId
                    ),
                    contentDescription = produto.nome,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = produto.nome,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextoMercadez,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatarPrecoMercadez(produto.precoBase),
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = VerdeMercadez
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Ver detalhes",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LaranjaMercadez
            )
        }
    }
}

// =====================================================
// SEÇÃO ECONOMIZE
// =====================================================

@Composable
fun SecaoEconomize(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE9C9)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "Compre melhor. Economize mais.",
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextoMercadez
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Compare os preços de diferentes mercados e descubra onde encontrar os produtos que você procura pelo melhor valor.",
                fontSize = 13.sp,
                color = Color(0xFF555555),
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Comparar preços agora",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// =====================================================
// TÍTULO DAS SEÇÕES
// =====================================================

@Composable
fun TituloSecao(
    titulo: String,
    subtitulo: String
) {

    Column {

        Text(
            text = titulo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextoMercadez
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitulo,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

// =====================================================
// BOTÃO CENTRAL
// =====================================================

@Composable
fun TextButtonCentral(
    texto: String,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = texto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = LaranjaMercadez,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onClick()
                }
                .padding(8.dp)
        )
    }
}

// =====================================================
// CARD DE AFILIADO
// =====================================================

@Composable
fun CardAfiliado(
    afiliado: Afiliado,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = afiliado.drawableId
                ),
                contentDescription = afiliado.nome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(5.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = afiliado.nome,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextoMercadez,
                textAlign = TextAlign.Center
            )
        }
    }
}

// =====================================================
// FORMATAÇÃO DE PREÇOS
// =====================================================

private fun formatarPrecoMercadez(preco: Double): String {

    val formato = java.text.NumberFormat.getCurrencyInstance(
        java.util.Locale("pt", "BR")
    )

    return formato.format(preco)
}
