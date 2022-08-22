package br.com.samuka182.feirasp.entities

import javax.persistence.*

@Entity
@Table(name = "distrito")
data class Distrito(
    @Id
    var id: Int? = null,
    var nome: String? = null
) : EntidadeBase()
