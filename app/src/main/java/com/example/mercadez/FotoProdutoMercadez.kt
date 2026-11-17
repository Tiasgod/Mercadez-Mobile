package com.example.mercadez

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

/** Foto remota do Nagumo, com o mascote como fallback. */
@Composable
fun FotoProdutoMercadez(
    imagemUrl: String?,
    descricao: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    if (imagemUrl.isNullOrBlank()) {
        Image(
            painter = painterResource(R.drawable.precinho_maior),
            contentDescription = descricao,
            modifier = modifier,
            contentScale = contentScale,
        )
    } else {
        AsyncImage(
            model = imagemUrl,
            contentDescription = descricao,
            modifier = modifier,
            contentScale = contentScale,
            placeholder = painterResource(R.drawable.precinho_maior),
            error = painterResource(R.drawable.precinho_maior),
            fallback = painterResource(R.drawable.precinho_maior),
        )
    }
}
