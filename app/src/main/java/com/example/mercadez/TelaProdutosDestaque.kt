package com.example.mercadez

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TelaProdutosDestaque() {

    var filtroAtivo by remember {
        mutableStateOf<FiltroPreco?>(null)
    }

    val produtosOferta = listOf(
        ProdutoOferta(
            nome = "Arroz Camil\n1kg",
            precoBase = 10.0,
            drawableId = R.drawable.arroz,
            corFundo = Color(0xFF38B83D)
        ),
        ProdutoOferta(
            nome = "Feijão\nNota 10\n1kg",
            precoBase = 10.0,
            drawableId = R.drawable.feijao,
            corFundo = Color(0xFF42A5E8)
        )
    )

    val produtosFiltrados = when (filtroAtivo) {

        FiltroPreco.MAIS_BARATOS -> produtosOferta.filter {
            it.precoBase <= 10.00
        }

        FiltroPreco.MAIS_CAROS -> produtosOferta.filter {
            it.precoBase >= 10.00
        }

        null -> produtosOferta
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFE7E7E7))
    ) {

        // =========================================================
        // CABEÇALHO
        // =========================================================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFFFFBA45)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Produtos em destaque",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // =========================================================
        // ÁREA DOS PRODUTOS
        // =========================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE69A))
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 20.dp,
                    bottom = 18.dp
                )
        ) {

            if (produtosFiltrados.isEmpty()) {

                Text(
                    text = "Nenhum produto encontrado nesse filtro.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            } else {

                produtosFiltrados.forEachIndexed { index, produto ->

                    CardOferta(
                        produto = produto,
                        imagemNaDireita = index % 2 == 1
                    )

                    if (index < produtosFiltrados.lastIndex) {

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }

        // =========================================================
        // FILTROS
        // =========================================================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7E7E7))
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 12.dp,
                    bottom = 20.dp
                )
        ) {

            Text(
                text = "Filtrar preços",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // =====================================================
            // FILTRO MAIS BARATOS
            // =====================================================

            FiltroBotao(
                titulo = "Filtrar mais baratos",
                preco = "R$0,99",
                ativo = filtroAtivo == FiltroPreco.MAIS_BARATOS,
                onClick = {

                    filtroAtivo =
                        if (filtroAtivo == FiltroPreco.MAIS_BARATOS) {
                            null
                        } else {
                            FiltroPreco.MAIS_BARATOS
                        }
                }
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // =====================================================
            // FILTRO MAIS CAROS
            // =====================================================

            FiltroBotao(
                titulo = "Filtrar mais caros",
                preco = "R$500,00",
                ativo = filtroAtivo == FiltroPreco.MAIS_CAROS,
                onClick = {

                    filtroAtivo =
                        if (filtroAtivo == FiltroPreco.MAIS_CAROS) {
                            null
                        } else {
                            FiltroPreco.MAIS_CAROS
                        }
                }
            )
        }
    }
}

// =============================================================
// CARTÃO DO PRODUTO
// =============================================================

@Composable
fun CardOferta(
    produto: ProdutoOferta,
    imagemNaDireita: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(produto.corFundo)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (imagemNaDireita) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.Start
        }
    ) {

        if (!imagemNaDireita) {

            // =====================================================
            // IMAGEM À ESQUERDA
            // =====================================================

            Image(
                painter = painterResource(
                    id = produto.drawableId
                ),
                contentDescription = produto.nome,
                modifier = Modifier
                    .size(82.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            InformacoesProduto(
                produto = produto
            )

        } else {

            // =====================================================
            // IMAGEM À DIREITA
            // =====================================================

            InformacoesProduto(
                produto = produto,
                alinhamento = Alignment.End
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Image(
                painter = painterResource(
                    id = produto.drawableId
                ),
                contentDescription = produto.nome,
                modifier = Modifier
                    .size(82.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// =============================================================
// INFORMAÇÕES DO PRODUTO
// =============================================================

@Composable
fun RowScope.InformacoesProduto(
    produto: ProdutoOferta,
    alinhamento: Alignment.Horizontal = Alignment.Start
) {

    Column(
        horizontalAlignment = alinhamento,
        modifier = Modifier.weight(1f)
    ) {

        Text(
            text = produto.nome,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = if (alinhamento == Alignment.End) {
                TextAlign.End
            } else {
                TextAlign.Start
            }
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFFFA726))
                .padding(
                    horizontal = 10.dp,
                    vertical = 3.dp
                )
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "por apenas",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "R$${String.format(
                        Locale("pt", "BR"),
                        "%.2f",
                        produto.precoBase
                    )}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

// =============================================================
// BOTÃO DE FILTRO
// =============================================================

@Composable
fun FiltroBotao(
    titulo: String,
    preco: String,
    ativo: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(
                if (ativo) {
                    Color(0xFFFFD27A)
                } else {
                    Color(0xFFE7E7E7)
                }
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(7.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                vertical = 7.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = titulo,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = preco,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}