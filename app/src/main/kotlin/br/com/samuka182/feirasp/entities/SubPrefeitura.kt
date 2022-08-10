package br.com.samuka182.feirasp.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(
    name = "sub_prefeitura",
    indexes = [Index(
        name = "sub_prefeitura_external_identifier_un",
        columnList = "identificador_externo",
        unique = true
    )]
)
data class SubPrefeitura(
    @field:Column(
        name = "nome", nullable = false, length = 36
    ) var nome: String? = null
) : EntidadeBase()