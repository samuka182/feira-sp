package br.com.samuka182.feirasp.entities

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(
    name = "feira_livre", indexes = [
        Index(name = "idx_bairro_feira", columnList = "bairro"),
        Index(name = "feira_livre_registro_un", columnList = "registro", unique = true),
        Index(name = "idx_nome_feira", columnList = "nome"),
        Index(name = "idx_regiao5", columnList = "regiao5")
    ]
)
data class FeiraLivre(
    @Id
    var id: Int? = null,
    var nome: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distrito_id")
    var distrito: Distrito? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_prefeitura_id")
    var subPrefeitura: SubPrefeitura? = null,
    var latitude: Int? = null,
    var longitude: Int? = null,
    var setorCenso: Long? = null,
    var areaPonderacao: Long? = null,
    var regiao5: String? = null,
    var regiao8: String? = null,
    var registro: String? = null,
    var logradouro: String? = null,
    var numero: Int? = null,
    var bairro: String? = null,
    var referencia: String? = null
) : EntidadeBase()
