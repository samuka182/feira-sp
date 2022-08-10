package br.com.samuka182.feirasp.entities

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.geo.Point
import javax.persistence.*

@Entity
@Table(
    name = "feira_livre", indexes = [Index(name = "idx_bairro_feira", columnList = "bairro"), Index(
        name = "feira_livre_nome_un", columnList = "nome", unique = true
    ), Index(name = "idx_regiao5", columnList = "regiao5")]
)
data class FeiraLivre(
    @field:Column(name = "nome", nullable = false, length = 36) var nome: String? = null,
    @field:ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    ) @field:OnDelete(action = OnDeleteAction.CASCADE) @field:JoinColumn(
        name = "distrito_id",
        nullable = false
    ) var distrito: Distrito? = null,
    @field:ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    ) @field:OnDelete(action = OnDeleteAction.CASCADE) @field:JoinColumn(
        name = "sub_prefeitura_id",
        nullable = false
    ) var subPrefeitura: SubPrefeitura? = null,
    @field:Column(name = "localizacao", columnDefinition = "point") var localizacao: Point? = null,
    @field:Column(name = "setor_censo") var setorCenso: Long? = null,
    @field:Column(name = "area_ponderacao") var areaPonderacao: Long? = null,
    @field:Column(name = "regiao5", nullable = false, length = 12) var regiao5: String? = null,
    @field:Column(name = "regiao8", length = 12) var regiao8: String? = null,
    @field:Column(name = "registro", nullable = false, length = 6) var registro: String? = null,
    @field:Column(name = "logradouro", length = 36) var logradouro: String? = null,
    @field:Column(name = "numero") var numero: Int? = null,
    @field:Column(name = "bairro", nullable = false, length = 36) var bairro: String? = null,
    @field:Column(name = "referencia", length = 36) var referencia: String? = null,
) : EntidadeBase()