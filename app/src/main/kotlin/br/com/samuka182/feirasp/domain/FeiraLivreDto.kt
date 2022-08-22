package br.com.samuka182.feirasp.domain

import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class FeiraLivreDto(
    @field:NotBlank val id: Int? = null,
    @field:NotBlank @field:Size(max = 6) @field:Pattern(
        regexp = "[0-9]{4}-[0-9]",
        message = "Formato de registro invalido. Formato valido (9999-9)"
    ) val registro: String? = null,
    val criadoEm: LocalDateTime? = null,
    val atualizadoEm: LocalDateTime? = null,
    @field:NotBlank @field:Size(max = 36) val nome: String? = null,
    @field:NotBlank val codDistrito: Int? = null,
    val nomeDistrito: String? = null,
    @field:NotBlank val codSubPrefeitura: Int? = null,
    val nomeSubPrefeitura: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null,
    val setorCenso: Long? = null,
    val areaPonderacao: Long? = null,
    @field:NotBlank @field:Size(max = 12) val regiao5: String? = null,
    @field:Size(max = 12) val regiao8: String? = null,
    @field:Size(max = 36) val logradouro: String? = null,
    val numero: String? = null,
    @field:NotBlank @field:Size(max = 36) val bairro: String? = null,
    @field:Size(max = 36) val referencia: String? = null
) : Serializable
