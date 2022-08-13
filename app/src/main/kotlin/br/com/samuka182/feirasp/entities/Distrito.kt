package br.com.samuka182.feirasp.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(
    name = "distrito",
    indexes = [Index(name = "distrito_external_identifier_un", columnList = "identificador_externo", unique = true)]
)
data class Distrito(
    @field:Column(name = "nome", nullable = false, length = 36) var nome: String? = null,
    @field:Column(name = "identificador_externo", length = 32) var identificadorExterno: String? = null,
) : EntidadeBase()