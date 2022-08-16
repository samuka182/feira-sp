package br.com.samuka182.feirasp.entities

import java.time.LocalDateTime
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
open class EntidadeBase(
    var criadoEm: LocalDateTime? = null,
    var atualizadoEm: LocalDateTime? = null,
) {
    @PrePersist
    fun prePersist() {
        LocalDateTime.now().let {
            criadoEm = it
            atualizadoEm = it
        }
    }

    @PreUpdate
    fun preUpdate() {
        atualizadoEm = LocalDateTime.now()
    }
}