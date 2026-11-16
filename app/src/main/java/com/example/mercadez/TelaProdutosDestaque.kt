
package com.example.mercadez

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val LaranjaMercadez = Color(0xFFFFA539)
private val AmareloMercadez = Color(0xFFFFC942)
private val FundoTela = Color(0xFFFFF9ED)
private val TextoEscuro = Color(0xFF292929)
private val VerdeMercadez = Color(0xFF258B43)

@Composable
fun TelaProdutosDestaque(
    onProdutoClick: (ProdutoOferta) -> Unit
) {

    var filtroAtivo by remember {
        mutableStateOf<FiltroPreco?>(null)
    }

    var pesquisa by remember {
        mutableStateOf("")
    }

    val produtosOferta = remember {
        listOf(
            ProdutoOferta(
                nome = "Arroz Camil 5kg",
                precoBase = 23.59,
                drawableId = R.drawable.arroz,
                corFundo = Color(0xFF3EAD58)
            ),
            ProdutoOferta(
                nome = "Feijão Nota 10 1kg",
                precoBase = 5.89,
                drawableId = R.drawable.feijao,
                corFundo = Color(0xFF3695D0)
            )
        )
    }

    val produtosEncontrados = produtosOferta.filter {
        it.nome.contains(pesquisa, ignoreCase = true)
    }

    val produtosFiltrados = when (filtroAtivo) {

        FiltroPreco.MAIS_BARATOS ->
            produtosEncontrados.sortedBy { it.precoBase }

        FiltroPreco.MAIS_CAROS ->
            produtosEncontrados.sortedByDescending { it.precoBase }

        null -> produtosEncontrados
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
            .verticalScroll(rememberScrollState())
    ) {

        // CABEÇALHO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            LaranjaMercadez,
                            AmareloMercadez
                        )
                    )
                )
                .padding(
                    horizontal = 22.dp,
                    vertical = 27.dp
                )
        ) {

            Column {

                Text(
                    text = "MERC ADEZ".replace(" ", ""),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = "Produtos em destaque",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Encontre seus produtos e confira os preços.",
                    fontSize = 13.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                        .padding(
                            horizontal = 13.dp,
                            vertical = 7.dp
                        )
                ) {
                    Text(
                        text = "OFERTAS DISPONÍVEIS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(22.dp))

            // RESUMO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(17.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(43.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(Color(0xFFFFE8C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = LaranjaMercadez,
                            modifier = Modifier.size(23.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {

                        Text(
                            text = "Tudo em um só lugar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Consulte os produtos e acesse seus detalhes.",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PESQUISA
            Text(
                text = "O que você procura?",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = TextoEscuro
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = pesquisa,
                onValueChange = {
                    pesquisa = it
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Digite o nome do produto...",
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar",
                        tint = LaranjaMercadez
                    )
                },
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // FILTROS
            Text(
                text = "Explore as ofertas",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = TextoEscuro
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Organize os produtos de acordo com o preço.",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(13.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {

                FilterChip(
                    selected = filtroAtivo == null,
                    onClick = {
                        filtroAtivo = null
                    },
                    label = {
                        Text("Todos")
                    }
                )

                FilterChip(
                    selected = filtroAtivo == FiltroPreco.MAIS_BARATOS,
                    onClick = {
                        filtroAtivo =
                            if (filtroAtivo == FiltroPreco.MAIS_BARATOS) {
                                null
                            } else {
                                FiltroPreco.MAIS_BARATOS
                            }
                    },
                    label = {
                        Text("Menor preço")
                    }
                )

                FilterChip(
                    selected = filtroAtivo == FiltroPreco.MAIS_CAROS,
                    onClick = {
                        filtroAtivo =
                            if (filtroAtivo == FiltroPreco.MAIS_CAROS) {
                                null
                            } else {
                                FiltroPreco.MAIS_CAROS
                            }
                    },
                    label = {
                        Text("Maior preço")
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // CONTAGEM DE PRODUTOS
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Produtos em destaque hoje",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoEscuro
                )

                Text(
                    text = "${produtosFiltrados.size} produto(s)",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            // CARDS DOS PRODUTOS
            if (produtosFiltrados.isEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(17.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(35.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Nenhum produto encontrado",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Tente pesquisar por outro nome.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

            } else {

                produtosFiltrados.forEach { produto ->

                    CardOferta(
                        produto = produto,
                        onClick = {
                            onProdutoClick(produto)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // CHAMADA PARA COMPARAÇÃO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE9C9)
                )
            ) {

                Column(
                    modifier = Modifier.padding(17.dp)
                ) {

                    Text(
                        text = "Quer economizar ainda mais?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextoEscuro
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Acesse os detalhes de cada produto para consultar informações e comparar preços.",
                        fontSize = 12.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// =====================================================
// CARD DE OFERTA
// =====================================================

@Composable
fun CardOferta(
    produto: ProdutoOferta,
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
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column {

            // FAIXA SUPERIOR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF0DA))
                    .padding(
                        horizontal = 13.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "PRODUTO EM DESTAQUE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFAD690F)
                )

                Text(
                    text = "Ver detalhes  >",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = LaranjaMercadez
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // IMAGEM
                Box(
                    modifier = Modifier
                        .size(116.dp)
                        .clip(RoundedCornerShape(16.dp))
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

                Spacer(modifier = Modifier.width(14.dp))

                // INFORMAÇÕES
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = produto.nome,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoEscuro
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Oferta disponível",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Preço cadastrado",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = formatarPreco(produto.precoBase),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VerdeMercadez
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFE7F5E9))
                            .padding(
                                horizontal = 10.dp,
                                vertical = 6.dp
                            )
                    ) {

                        Text(
                            text = "Consultar produto",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerdeMercadez
                        )
                    }
                }
            }
        }
    }
}

// =====================================================
// FORMATAÇÃO DE PREÇO
// =====================================================

private fun formatarPreco(preco: Double): String {

    val formato = NumberFormat.getCurrencyInstance(
        Locale("pt", "BR")
    )

    return formato.format(preco)
}
