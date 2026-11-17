package com.example.mercadez

import androidx.compose.runtime.*
import com.example.mercadez.api.ProdutoRealApi
import com.example.mercadez.api.RetrofitClient
import kotlinx.coroutines.CancellationException

/**
 * Carrega produtos ativos com fotografia cadastrada, diretamente do Railway.
 * O banco filtra antes de paginar: nao e mais necessario amostrar 40 mil itens.
 * A busca por texto continua independente, em /produtos/buscar.
 */
@Composable
fun lembrarProdutosNagumo(): EstadoProdutosNagumo {
    var produtos by remember { mutableStateOf<List<ProdutoRealApi>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf<String?>(null) }
    var tentativa by remember { mutableIntStateOf(0) }

    LaunchedEffect(tentativa) {
        carregando = true
        erro = null
        produtos = emptyList()
        try {
            val encontrados = linkedMapOf<Int, ProdutoRealApi>()
            val tamanhoPagina = 100
            // Carrega ate 3 paginas (300 produtos com foto) por abertura.
            // A API aplica com_imagem antes do limit/offset.
            for (pagina in 0 until 3) {
                val resposta = RetrofitClient.api.listarProdutos(
                    limit = tamanhoPagina,
                    offset = pagina * tamanhoPagina,
                    comImagem = true
                )
                if (!resposta.isSuccessful) {
                    error("HTTP ${resposta.code()}")
                }
                val lote = resposta.body().orEmpty()
                lote.filter {
                    it.mercado.contains("nagumo", ignoreCase = true) &&
                        it.preco > 0.0 &&
                        !it.imagemUrl.isNullOrBlank()
                }.forEach { encontrados[it.id] = it }

                // Atualiza os cards a cada pagina recebida.
                produtos = encontrados.values.toList()
                if (lote.size < tamanhoPagina) break
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            erro = if (produtos.isNotEmpty()) null else
                "Não foi possível consultar os produtos (${e.message ?: "falha de conexão"})."
        } finally {
            carregando = false
        }
    }
    return EstadoProdutosNagumo(produtos, carregando, erro, { tentativa++ })
}

data class EstadoProdutosNagumo(
    val produtos: List<ProdutoRealApi>,
    val carregando: Boolean,
    val erro: String?,
    val tentarNovamente: () -> Unit
)
