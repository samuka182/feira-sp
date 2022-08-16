package br.com.samuka182.feirasp.domain

import java.io.Serializable
import java.time.LocalDateTime

data class FeiraLivreDto(
    val id: Int? = null,
    val registro: String? = null,
    val criadoEm: LocalDateTime? = null,
    val atualizadoEm: LocalDateTime? = null,
    val nome: String? = null,
    val codDistrito: Int? = null,
    val nomeDistrito: String? = null,
    val codSubPrefeitura: Int? = null,
    val nomeSubPrefeitura: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null,
    val setorCenso: Long? = null,
    val areaPonderacao: Long? = null,
    val regiao5: String? = null,
    val regiao8: String? = null,
    val logradouro: String? = null,
    val numero: Int? = null,
    val bairro: String? = null,
    val referencia: String? = null
) : Serializable
