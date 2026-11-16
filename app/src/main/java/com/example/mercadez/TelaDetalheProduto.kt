
package com.example.mercadez

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val LaranjaDetalhe = Color(0xFFFFA539)
private val FundoDetalhe = Color(0xFFF5F5F5)
private val TextoDetalhe = Color(0xFF252525)
private val TextoCinza = Color(0xFF777777)
private val VerdePreco = Color(0xFF258A37)

private data class MercadoDetalhe(
    val nome: String,
    val preco: Double,
    val distancia: String,
    val atualizacao: String,
    val melhorPreco: Boolean = false
)

private data class DadosDetalhe(
    val nome: String,
    val descricao: String,
    val fabricante: String,
    val embalagem: String,
    val imagem: Int,
    val mercados: List<MercadoDetalhe>,
    val historico: List<Float>
)

private fun obterDadosProduto(produtoId: String): DadosDetalhe {

    return if (produtoId == "feijao") {

        DadosDetalhe(
            nome = "Feijão Nota 10",
            descricao = "Feijão",
            fabricante = "Informação não cadastrada",
            embalagem = "1 kg",
            imagem = R.drawable.feijao,
            mercados = emptyList(),
            historico = emptyList()
        )

    } else {

        DadosDetalhe(
            nome = "Arroz Camil Tipo 1",
            descricao = "Longo Fino",
            fabricante = "Camil Alimentos S.A.",
            embalagem = "5 kg",
            imagem = R.drawable.arroz,
            mercados = listOf(
                MercadoDetalhe(
                    "Supermercado Super Mais",
                    24.99,
                    "1,2 km",
                    "Hoje, 08:30",
                    true
                ),
                MercadoDetalhe(
                    "Mercado Bom Preço",
                    27.49,
                    "2,4 km",
                    "Ontem, 16:00"
                ),
                MercadoDetalhe(
                    "Mercado Central",
                    29.90,
                    "3,1 km",
                    "Ontem, 14:22"
                ),
                MercadoDetalhe(
                    "Atacadão Família",
                    30.50,
                    "5,8 km",
                    "2 dias atrás"
                ),
                MercadoDetalhe(
                    "HiperMais Atacado",
                    31.90,
                    "7,2 km",
                    "3 dias atrás"
                )
            ),
            historico = listOf(
                35f, 32f, 30f, 31f, 28f, 27f, 25f
            )
        )
    }
}

private fun moeda(valor: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        .format(valor)
}

@Composable
fun TelaDetalheProduto(produtoId: String) {

    val produto = obterDadosProduto(produtoId)

    val melhorPreco = produto.mercados.minByOrNull {
        it.preco
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoDetalhe)
            .verticalScroll(rememberScrollState())
    ) {

        // Cabeçalho do produto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LaranjaDetalhe)
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Detalhes do produto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // Informações principais
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(produto.imagem),
                            contentDescription = produto.nome,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = produto.nome,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoDetalhe,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${produto.descricao} • ${produto.embalagem}",
                        fontSize = 14.sp,
                        color = TextoCinza
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Fabricante: ${produto.fabricante}",
                        fontSize = 12.sp,
                        color = TextoCinza,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE8F5E9))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Melhor preço encontrado",
                                fontSize = 13.sp,
                                color = Color(0xFF347A3B)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = melhorPreco?.let {
                                    moeda(it.preco)
                                } ?: "Preço não disponível",
                                fontSize = 27.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerdePreco
                            )

                            if (melhorPreco != null) {
                                Text(
                                    text = "Em ${melhorPreco.nome}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF347A3B),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Comparação de preços
            TituloSecaoDetalhe(
                titulo = "Comparação de preços",
                descricao = "Compare os valores encontrados nos mercados."
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (produto.mercados.isEmpty()) {

                EstadoSemDados(
                    mensagem = "Ainda não existem preços cadastrados para este produto."
                )

            } else {

                produto.mercados.forEachIndexed { index, mercado ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(13.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (mercado.melhorPreco) {
                                            Color(0xFFE8F5E9)
                                        } else {
                                            Color(0xFFF1F1F1)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    color = if (mercado.melhorPreco) {
                                        VerdePreco
                                    } else {
                                        TextoDetalhe
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = mercado.nome,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoDetalhe
                                )

                                Text(
                                    text = "${mercado.distancia} • ${mercado.atualizacao}",
                                    fontSize = 10.sp,
                                    color = TextoCinza
                                )

                                if (mercado.melhorPreco) {
                                    Text(
                                        text = "MENOR PREÇO",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VerdePreco
                                    )
                                }
                            }

                            Text(
                                text = moeda(mercado.preco),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (mercado.melhorPreco) {
                                    VerdePreco
                                } else {
                                    TextoDetalhe
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Valores demonstrativos da página de referência. Consulte os mercados para confirmar os preços.",
                    fontSize = 11.sp,
                    color = TextoCinza,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Histórico de preços
            TituloSecaoDetalhe(
                titulo = "Histórico de preços",
                descricao = "Acompanhe a variação dos valores ao longo do tempo."
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Evolução dos preços",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoDetalhe
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = if (produto.historico.isNotEmpty()) {
                            "Exemplo visual com valores ilustrativos"
                        } else {
                            "Nenhum registro histórico disponível"
                        },
                        fontSize = 11.sp,
                        color = TextoCinza
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    if (produto.historico.isNotEmpty()) {

                        GraficoHistorico(
                            valores = produto.historico
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            listOf(
                                "Dez", "Jan", "Fev", "Mar",
                                "Abr", "Mai", "Hoje"
                            ).forEach {
                                Text(
                                    text = it,
                                    fontSize = 10.sp,
                                    color = TextoCinza
                                )
                            }
                        }

                    } else {

                        EstadoSemDados(
                            mensagem = "O histórico será exibido quando houver registros de preços."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TituloSecaoDetalhe(
    titulo: String,
    descricao: String
) {
    Column {
        Text(
            text = titulo,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = TextoDetalhe
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = descricao,
            fontSize = 12.sp,
            color = TextoCinza
        )
    }
}

@Composable
private fun EstadoSemDados(
    mensagem: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mensagem,
            fontSize = 12.sp,
            color = TextoCinza,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GraficoHistorico(
    valores: List<Float>
) {

    val maximo = valores.maxOrNull() ?: 35f
    val minimo = valores.minOrNull() ?: 20f

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
    ) {

        val largura = size.width
        val altura = size.height
        val margem = 12f

        // Linhas horizontais
        for (i in 0..3) {
            val y = margem + (altura - margem * 2) * i / 3

            drawLine(
                color = Color(0xFFEAEAEA),
                start = Offset(0f, y),
                end = Offset(largura, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        if (valores.size > 1) {

            val intervalo = (maximo - minimo).coerceAtLeast(1f)
            val pontos = valores.mapIndexed { index, valor ->

                val x = largura * index / (valores.size - 1)
                val y = altura - margem -
                        ((valor - minimo) / intervalo) * (altura - margem * 2)

                Offset(x, y)
            }

            val caminho = Path().apply {
                moveTo(pontos.first().x, pontos.first().y)

                pontos.drop(1).forEach {
                    lineTo(it.x, it.y)
                }
            }

            drawPath(
                path = caminho,
                color = LaranjaDetalhe,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )

            pontos.forEach { ponto ->
                drawCircle(
                    color = LaranjaDetalhe,
                    radius = 4.dp.toPx(),
                    center = ponto
                )

                drawCircle(
                    color = Color.White,
                    radius = 1.8.dp.toPx(),
                    center = ponto
                )
            }
        }
    }
}
