package br.com.samuka182.feirasp.domain

data class RetornoDto(
    val codigo: Int,
    val status: String,
    val dados: Any? = null)