package com.example.mercadez

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TelaComparacaoPrecosMercadez() {
    val estado = lembrarProdutosNagumo()
    var busca by remember { mutableStateOf("") }
    val grupos = estado.produtos.groupBy { it.nomeProduto.trim().lowercase() }.values
        .filter { grupo -> grupo.any { it.nomeProduto.contains(busca, ignoreCase = true) } }
        .sortedBy { it.first().nomeProduto }
    Column(Modifier.fillMaxSize().background(Color(0xFFFFF9ED))) {
        Text("Comparar preços Nagumo", Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        OutlinedTextField(busca, { busca = it }, Modifier.fillMaxWidth().padding(horizontal = 16.dp), label = { Text("Pesquisar produto") }, singleLine = true)
        Text("Comparação por nome idêntico do produto entre unidades cadastradas.", Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (estado.carregando) item { CircularProgressIndicator() }
            else if (estado.erro != null) item { Column { Text(estado.erro.orEmpty()); TextButton(onClick = estado.tentarNovamente) { Text("Tentar novamente") } } }
            else if (grupos.isEmpty()) item { Text("Nenhum produto Nagumo encontrado.") }
            else items(grupos) { grupo ->
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(grupo.first().nomeProduto, fontWeight = FontWeight.Bold)
                        grupo.sortedBy { it.preco }.forEach { item ->
                            Text("${item.mercado}: ${precoBR(item.preco)}", Modifier.padding(top = 6.dp), color = Color(0xFF424242))
                        }
                        if (grupo.map { it.mercado }.distinct().size < 2) Text("Sem outra unidade com o mesmo produto para comparar.", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
