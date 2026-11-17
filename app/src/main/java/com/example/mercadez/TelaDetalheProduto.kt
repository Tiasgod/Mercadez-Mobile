package com.example.mercadez

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TelaDetalheProduto(produtoId: String) {
    val estado = lembrarProdutosNagumo()
    val produto = estado.produtos.firstOrNull { it.id.toString() == produtoId }
    Column(Modifier.fillMaxSize().background(Color(0xFFFFF9ED)).verticalScroll(rememberScrollState()).padding(18.dp)) {
        Text("Detalhes do produto", style = MaterialTheme.typography.headlineSmall, color = Color(0xFFE65C00), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        if (estado.carregando) CircularProgressIndicator()
        else if (estado.erro != null) { Text(estado.erro.orEmpty()); TextButton(onClick = estado.tentarNovamente) { Text("Tentar novamente") } }
        else if (produto == null) Text("Produto não encontrado entre os itens cadastrados do Nagumo.")
        else {
            Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(produto.nomeProduto, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Unidade: ${produto.mercado}")
                    Text("Preço cadastrado: ${precoBR(produto.preco)}", color = Color(0xFFE65C00), style = MaterialTheme.typography.titleMedium)
                    Text("Quantidade cadastrada: ${produto.quantidade}")
                    if (!produto.tags.isNullOrBlank()) Text("Tags: ${produto.tags}")
                    Text("Fonte: API Mercadez / cadastro de produtos", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.height(14.dp))
            Text("Outras unidades Nagumo com o mesmo nome de produto", fontWeight = FontWeight.Bold)
            estado.produtos.filter { it.nomeProduto.equals(produto.nomeProduto, ignoreCase = true) && it.id != produto.id }.forEach {
                Text("${it.mercado}: ${precoBR(it.preco)}", Modifier.padding(vertical = 6.dp))
            }
        }
    }
}
