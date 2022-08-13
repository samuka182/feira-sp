package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.Distrito
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DistritoRepository : JpaRepository<Distrito, UUID> {

    fun findByIdentificadorExterno(value: String): Distrito?

}