package br.com.samuka182.feirasp.domain

import java.io.Serializable
import java.time.LocalDateTime

data class FeiraLivreDto(
    val id: String? = null,
    val registro: String? = null,
    val criadoEm: LocalDateTime? = null,
    val atualizadoEm: LocalDateTime? = null,
    val nome: String? = null,
    val codDistrito: String? = null,
    val nomeDistrito: String? = null,
    val codSubPrefeitura: String? = null,
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
