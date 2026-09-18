package com.example.mercadez

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.Normalizer
import java.util.Locale


// ============================================================
// MODELOS
// ============================================================

data class PrecoComparacao(
    val mercado: String,
    val preco: Double
)

data class ProdutoComparacao(
    val nome: String,
    val imagem: Int,
    val precos: List<PrecoComparacao>
)


// ============================================================
// FUNÇÃO PARA REMOVER ACENTOS
// ============================================================

fun removerAcentos(texto: String): String {
    return Normalizer
        .normalize(texto, Normalizer.Form.NFD)
        .replace(
            "\\p{InCombiningDiacriticalMarks}+".toRegex(),
            ""
        )
        .lowercase(Locale.getDefault())
}


// ============================================================
// TELA DE COMPARAÇÃO DE PREÇOS
// ============================================================

@Composable
fun TelaComparacaoPrecosMercadez() {

    var pesquisa by remember {
        mutableStateOf("")
    }

    var produtoSelecionado by remember {
        mutableStateOf<ProdutoComparacao?>(null)
    }


    // ========================================================
    // PRODUTOS
    // ========================================================

    val produtos = listOf(

        // 1. Leite
        ProdutoComparacao(
            nome = "Leite Integral 1L",
            imagem = R.drawable.leite,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 5.49),
                PrecoComparacao("Tá na Cesta", 6.29),
                PrecoComparacao("Mercado do Bode", 5.89),
                PrecoComparacao("Banana Feliz", 6.49)
            )
        ),

        // 2. Arroz
        ProdutoComparacao(
            nome = "Arroz 5kg",
            imagem = R.drawable.arroz,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 24.90),
                PrecoComparacao("Tá na Cesta", 27.49),
                PrecoComparacao("Mercado do Bode", 25.90),
                PrecoComparacao("Banana Feliz", 26.99)
            )
        ),

        // 3. Feijão
        ProdutoComparacao(
            nome = "Feijão 1kg",
            imagem = R.drawable.feijao,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 7.49),
                PrecoComparacao("Tá na Cesta", 8.29),
                PrecoComparacao("Mercado do Bode", 7.89),
                PrecoComparacao("Banana Feliz", 8.49)
            )
        ),

        // 4. Café
        ProdutoComparacao(
            nome = "Café 500g",
            imagem = R.drawable.cafe,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 18.90),
                PrecoComparacao("Tá na Cesta", 21.49),
                PrecoComparacao("Mercado do Bode", 19.90),
                PrecoComparacao("Banana Feliz", 20.99)
            )
        ),

        // 5. Açúcar
        ProdutoComparacao(
            nome = "Açúcar 1kg",
            imagem = R.drawable.acucar,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 4.29),
                PrecoComparacao("Tá na Cesta", 4.79),
                PrecoComparacao("Mercado do Bode", 4.49),
                PrecoComparacao("Banana Feliz", 5.09)
            )
        ),

        // 6. Óleo
        ProdutoComparacao(
            nome = "Óleo de Soja 900ml",
            imagem = R.drawable.oleo,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 6.29),
                PrecoComparacao("Tá na Cesta", 7.19),
                PrecoComparacao("Mercado do Bode", 6.59),
                PrecoComparacao("Banana Feliz", 7.49)
            )
        ),

        // 7. Macarrão
        ProdutoComparacao(
            nome = "Macarrão 500g",
            imagem = R.drawable.macarrao,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 3.49),
                PrecoComparacao("Tá na Cesta", 4.29),
                PrecoComparacao("Mercado do Bode", 3.79),
                PrecoComparacao("Banana Feliz", 4.49)
            )
        ),

        // 8. Molho de tomate
        ProdutoComparacao(
            nome = "Molho de Tomate 300g",
            imagem = R.drawable.molho_tomate,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 2.49),
                PrecoComparacao("Tá na Cesta", 2.99),
                PrecoComparacao("Mercado do Bode", 2.69),
                PrecoComparacao("Banana Feliz", 3.19)
            )
        ),

        // 9. Detergente
        ProdutoComparacao(
            nome = "Detergente 500ml",
            imagem = R.drawable.detergente,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 2.29),
                PrecoComparacao("Tá na Cesta", 2.79),
                PrecoComparacao("Mercado do Bode", 2.49),
                PrecoComparacao("Banana Feliz", 2.99)
            )
        ),

        // 10. Sabão em pó
        ProdutoComparacao(
            nome = "Sabão em Pó 1kg",
            imagem = R.drawable.sabao_po,
            precos = listOf(
                PrecoComparacao("Mercado Mais", 16.99),
                PrecoComparacao("Tá na Cesta", 17.49),
                PrecoComparacao("Mercado do Bode", 19.49),
                PrecoComparacao("Banana Feliz", 20.99)
            )
        )
    )


    // ========================================================
    // FUNDO DA TELA
    // ========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // ====================================================
        // TÍTULO
        // ====================================================

        Text(
            text = "Comparação de Preços",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Pesquise um produto e compare os preços entre os mercados.",
            fontSize = 15.sp,
            color = Color.DarkGray
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // ====================================================
        // CAMPO DE PESQUISA
        // ====================================================

        OutlinedTextField(
            value = pesquisa,

            onValueChange = {
                pesquisa = it
            },

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Pesquisar produto")
            },

            placeholder = {
                Text("Ex.: arroz, feijão, café...")
            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar"
                )
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // ====================================================
        // BOTÃO PESQUISAR
        // ====================================================

        Button(
            onClick = {

                if (pesquisa.isNotBlank()) {

                    // Remove acentos do que o usuário digitou
                    val termoPesquisa =
                        removerAcentos(
                            pesquisa.trim()
                        )

                    // Procura ignorando acentos
                    produtoSelecionado =
                        produtos.firstOrNull { produto ->

                            val nomeProduto =
                                removerAcentos(
                                    produto.nome
                                )

                            nomeProduto.contains(
                                termoPesquisa
                            )
                        }
                }

            },

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA000)
            ),

            shape = RoundedCornerShape(10.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "Comparar preços",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // ====================================================
        // RESULTADO
        // ====================================================

        if (produtoSelecionado == null) {

            // -----------------------------------------------
            // NENHUM PRODUTO SELECIONADO
            // -----------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Pesquise um produto",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Digite o nome do produto acima.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

        } else {

            // -----------------------------------------------
            // PRODUTO ENCONTRADO
            // -----------------------------------------------

            val produto = produtoSelecionado!!

            val menorPreco =
                produto.precos.minOf {
                    it.preco
                }

            val maiorPreco =
                produto.precos.maxOf {
                    it.preco
                }

            val economia =
                maiorPreco - menorPreco


            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(
                    12.dp
                )
            ) {

                // ============================================
                // PRODUTO
                // ============================================

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF7F7F7)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Image(
                                painter =
                                    painterResource(
                                        id = produto.imagem
                                    ),

                                contentDescription =
                                    produto.nome,

                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            12.dp
                                        )
                                    ),

                                contentScale =
                                    ContentScale.Crop
                            )

                            Spacer(
                                modifier = Modifier.width(16.dp)
                            )

                            Column {

                                Text(
                                    text = produto.nome,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(6.dp)
                                )

                                Text(
                                    text =
                                        "${produto.precos.size} mercados encontrados",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }


                // ============================================
                // MELHOR PREÇO
                // ============================================

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFE8F5E9)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Melhor preço",
                                fontSize = 15.sp,
                                color = Color.DarkGray
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(
                                text =
                                    "R$ ${"%.2f".format(menorPreco)}",
                                fontSize = 28.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF2E7D32)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(
                                text =
                                    "Você pode economizar até R$ ${
                                        "%.2f".format(economia)
                                    }",
                                fontSize = 14.sp,
                                color =
                                    Color(0xFF388E3C)
                            )
                        }
                    }
                }


                // ============================================
                // TÍTULO DOS PREÇOS
                // ============================================

                item {

                    Text(
                        text = "Preços encontrados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }


                // ============================================
                // LISTA DE MERCADOS
                // ============================================

                items(
                    produto.precos.sortedBy {
                        it.preco
                    }
                ) { preco ->

                    CardPreco(
                        preco = preco,
                        menorPreco = menorPreco
                    )
                }
            }
        }
    }
}


// ============================================================
// CARD DE CADA PREÇO
// ============================================================

@Composable
fun CardPreco(
    preco: PrecoComparacao,
    menorPreco: Double
) {

    val ehMenorPreco =
        preco.preco == menorPreco

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                if (ehMenorPreco) {
                    Color(0xFFFFF8E1)
                } else {
                    Color(0xFFF5F5F5)
                }
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = preco.mercado,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                if (ehMenorPreco) {

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(
                        text = "Menor preço",
                        fontSize = 13.sp,
                        color = Color(0xFF2E7D32),
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }

            Text(
                text =
                    "R$ ${"%.2f".format(preco.preco)}",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color =
                    if (ehMenorPreco) {
                        Color(0xFF2E7D32)
                    } else {
                        Color.Black
                    }
            )
        }
    }
}