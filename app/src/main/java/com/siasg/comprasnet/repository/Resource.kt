package com.siasg.comprasnet.repository

class Resource<T> (
    val dado: T?,
    val erro: String? = null
)

fun <T> resourceFalha(
    resourceAtual: Resource<T?>?,
    e: String?
): Resource<T?> {
    if (resourceAtual != null) {
        return Resource(dado = resourceAtual.dado, erro = e)
    }
    return Resource(dado = null, erro = e)
}