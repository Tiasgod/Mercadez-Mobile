package com.example.mercadez

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.mercadez.api.RetrofitClient
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mercadez.api.ProdutoRealApi
import java.text.NumberFormat
import java.util.Locale

private val laranja = Color(0xFFFF8900)
internal fun precoBR(v: Double): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(v)

@Composable
fun TelaProdutosDestaque(buscaInicial: String = "", onProdutoClick: (ProdutoRealApi) -> Unit) {
    val estado = lembrarProdutosNagumo()
    var busca by remember(buscaInicial) { mutableStateOf(buscaInicial) }
    var ordem by remember { mutableStateOf(0) }
    var resultadosBusca by remember { mutableStateOf<List<ProdutoRealApi>>(emptyList()) }
    var buscando by remember { mutableStateOf(false) }
    var erroBusca by remember { mutableStateOf<String?>(null) }
    val termo = busca.trim()

    // A pesquisa usa o backend inteiro, não apenas a amostra da página inicial.
    LaunchedEffect(termo) {
        resultadosBusca = emptyList()
        erroBusca = null
        buscando = false
        if (termo.length >= 2) {
            delay(350) // evita uma requisição a cada tecla digitada
            buscando = true
            try {
                val resposta = RetrofitClient.api.buscarProdutos(termo)
                if (!resposta.isSuccessful) error("HTTP ${resposta.code()}")
                resultadosBusca = resposta.body().orEmpty().filter {
                    it.mercado.contains("nagumo", ignoreCase = true) && it.preco > 0.0
                }
            } catch (e: Exception) {
                erroBusca = "Falha ao pesquisar: ${e.message ?: "sem conexão"}"
            } finally {
                buscando = false
            }
        }
    }

    // Destaques sem pesquisa: somente produtos com URL de imagem.
    // Durante a pesquisa: todos os produtos encontrados, inclusive sem imagem.
    val base = if (termo.length >= 2) resultadosBusca else
        estado.produtos.filter { !it.imagemUrl.isNullOrBlank() }
    val filtrados = base.let { lista -> when (ordem) {
        1 -> lista.sortedBy { it.preco }
        2 -> lista.sortedByDescending { it.preco }
        else -> lista
    } }
    val carregandoLista = if (termo.length >= 2) buscando else estado.carregando
    val erroLista = if (termo.length >= 2) erroBusca else estado.erro
    Column(Modifier.fillMaxSize().background(Color(0xFFFFF9ED))) {
        Text("Produtos dos supermercados Nagumo", Modifier.padding(18.dp), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = laranja)
        OutlinedTextField(busca, { busca = it }, Modifier.fillMaxWidth().padding(horizontal = 16.dp), placeholder = { Text("Buscar produtos ou unidade") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true)
        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Todos", "Menor preço", "Maior preço").forEachIndexed { index, nome ->
                FilterChip(selected = ordem == index, onClick = { ordem = index }, label = { Text(nome) })
            }
        }
        Text("${filtrados.size} produtos encontrados • preços consultados na API", Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.bodySmall)
        LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (carregandoLista) item { CircularProgressIndicator(Modifier.padding(24.dp)) }
            else if (erroLista != null) item { Column { Text(erroLista); if (termo.length < 2) TextButton(onClick = estado.tentarNovamente) { Text("Tentar novamente") } } }
            else if (filtrados.isEmpty()) item { Text(if (termo.length >= 2) "Nenhum produto Nagumo encontrado para esta pesquisa." else "Nenhum produto com foto disponível nos destaques.", Modifier.padding(16.dp)) }
            else items(filtrados, key = { it.id }) { produto ->
                Card(onClick = { onProdutoClick(produto) }, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        FotoProdutoMercadez(produto.imagemUrl, produto.nomeProduto, Modifier.size(60.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(produto.nomeProduto, fontWeight = FontWeight.Bold)
                            Text(produto.mercado, style = MaterialTheme.typography.bodySmall)
                            Text(precoBR(produto.preco), color = laranja, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
