package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.SubPrefeitura
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubPrefeituraRepository : JpaRepository<SubPrefeitura, UUID> {

    fun findByIdentificadorExterno(value: String): SubPrefeitura?

}