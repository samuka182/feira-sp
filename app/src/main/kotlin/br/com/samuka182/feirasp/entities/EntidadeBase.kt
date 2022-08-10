package br.com.samuka182.feirasp.entities

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class EntidadeBase {
    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @Column(name = "identificador_externo", length = 32)
    open var identificadorExterno: String? = null

    @Column(name = "criado_em", nullable = false)
    open var criadoEm: LocalDateTime? = null

    @Column(name = "atualizado_em", nullable = false)
    open var atualizadoEm: LocalDateTime? = null

    @PrePersist
    fun prePersist() {
        id = UUID.randomUUID()
        criadoEm = LocalDateTime.now()
        atualizadoEm = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        criadoEm = LocalDateTime.now()
    }

}