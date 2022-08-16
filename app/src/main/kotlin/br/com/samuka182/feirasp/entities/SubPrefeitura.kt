package br.com.samuka182.feirasp.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sub_prefeitura")
data class SubPrefeitura(
    @Id
    var id: Int? = null,
    var nome: String? = null,
) : EntidadeBase()
