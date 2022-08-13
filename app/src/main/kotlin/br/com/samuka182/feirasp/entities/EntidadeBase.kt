package br.com.samuka182.feirasp.entities

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class EntidadeBase(
    @field:Id @field:Column(name = "id", nullable = false) var id: UUID? = null,
    @field:Column(name = "criado_em", nullable = false) var criadoEm: LocalDateTime? = null,
    @field:Column(name = "atualizado_em", nullable = false) var atualizadoEm: LocalDateTime? = null
) {
    @PrePersist
    fun prePersist() {
        id = UUID.randomUUID()
        LocalDateTime.now().let { now ->
            criadoEm = now
            atualizadoEm = now
        }
    }

    @PreUpdate
    fun preUpdate() {
        atualizadoEm = LocalDateTime.now()
    }
}