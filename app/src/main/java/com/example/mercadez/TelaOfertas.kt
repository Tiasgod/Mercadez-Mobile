package com.example.mercadez

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mercadez.api.ProdutoRealApi
import com.example.mercadez.api.RetrofitClient
import java.text.NumberFormat
import java.util.Locale

private val laranja = Color(0xFFFF8800)
private val creme = Color(0xFFFFF9EE)
private fun dinheiro(v: Double) = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(v)

@Composable
fun TelaOfertas(
    onSobre: () -> Unit = {},
    onProdutos: () -> Unit = {},
    onConta: () -> Unit = {}
) {
    val estado = lembrarProdutosNagumo()
    var busca by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("Todas") }

    // O backend não devolve categoria. Para não inventar classificações,
    // os filtros são aplicados por palavras do nome; itens desconhecidos ficam em Todas.
    val categorias = listOf("Todas", "Alimentos", "Bebidas", "Higiene", "Limpeza")
    val exibidas = estado.produtos.filter { oferta ->
        val nome = oferta.nomeProduto.lowercase(Locale("pt", "BR"))
        val correspondeBusca = nome.contains(busca.trim().lowercase(Locale("pt", "BR"))) ||
            oferta.mercado.lowercase(Locale("pt", "BR")).contains(busca.trim().lowercase(Locale("pt", "BR")))
        val correspondeCategoria = when (categoria) {
            "Alimentos" -> listOf("arroz", "feijão", "macarrão", "açúcar", "óleo", "banana", "leite", "café", "farinha", "biscoito").any(nome::contains)
            "Bebidas" -> listOf("suco", "refrigerante", "água", "cerveja", "chá", "energético").any(nome::contains)
            "Higiene" -> listOf("sabonete", "shampoo", "pasta dental", "desodorante", "papel higiênico").any(nome::contains)
            "Limpeza" -> listOf("detergente", "sabão", "amaciante", "desinfetante", "água sanitária").any(nome::contains)
            else -> true
        }
        correspondeBusca && correspondeCategoria
    }

    Column(Modifier.fillMaxSize().background(creme)) {
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(bottom = 16.dp)) {
            item {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(value = busca, onValueChange = { busca = it }, modifier = Modifier.weight(1f),
                        placeholder = { Text("Buscar no Mercadez") }, singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Search, null) }, shape = RoundedCornerShape(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Box(Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFFFE5BE)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = laranja)
                    }
                }
                Text("Preços dos Nagumos", Modifier.padding(horizontal = 18.dp), fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE94D14))
                Text("Confira os preços reais cadastrados nas unidades Nagumo.", Modifier.padding(start = 18.dp, end = 18.dp, top = 5.dp, bottom = 14.dp), fontSize = 14.sp)
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categorias) { item ->
                        FilterChip(selected = categoria == item, onClick = { categoria = item },
                            label = { Text(item) }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = laranja, selectedLabelColor = Color.White))
                    }
                }
                Row(Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(22.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFFFFB500), Color(0xFFFF7100))))
                    .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource(R.drawable.precinho_maior), "Precinho, mascote Mercadez", Modifier.size(102.dp), contentScale = ContentScale.Fit)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Produtos Nagumo", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Text("Preços consultados diretamente no Mercadez!", color = Color.White, fontSize = 13.sp)
                    }
                }
            }
            if (estado.carregando) item { Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = laranja) } }
            else if (estado.erro != null) item {
                Column(Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(estado.erro.orEmpty())
                    TextButton(onClick = { estado.tentarNovamente() }) { Text("Tentar novamente") }
                }
            }
            else if (exibidas.isEmpty()) item {
                Text("Nenhuma oferta encontrada para este filtro.", Modifier.padding(24.dp))
            }
            else items(exibidas, key = { it.id }) { oferta -> OfertaCardReal(oferta) }
        }
        NavigationBar(containerColor = Color.White) {
            NavigationBarItem(selected = false, onClick = onSobre, icon = { Icon(Icons.Default.Info, null) }, label = { Text("Sobre") })
            NavigationBarItem(selected = false, onClick = onProdutos, icon = { Icon(Icons.Default.GridView, null) }, label = { Text("Produtos") })
            NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Percent, null) }, label = { Text("Ofertas") },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = laranja, selectedTextColor = laranja))
            NavigationBarItem(selected = false, onClick = onConta, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Minha conta") })
        }
    }
}

@Composable
private fun OfertaCardReal(oferta: ProdutoRealApi) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp), shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(76.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFFFF1DD)), contentAlignment = Alignment.Center) {
                FotoProdutoMercadez(oferta.imagemUrl, oferta.nomeProduto, Modifier.size(68.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(oferta.nomeProduto, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 3)
                Text(oferta.mercado, fontSize = 12.sp, color = Color.DarkGray)
                Spacer(Modifier.height(5.dp))
                Text(precoBR(oferta.preco), fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE95A13))
                Text("Preço cadastrado no Mercadez", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}
