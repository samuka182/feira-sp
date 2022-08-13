package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.FeiraLivre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeiraLivreRepository : JpaRepository<FeiraLivre, UUID> {

    fun findByIdentificadorExterno(value: String): FeiraLivre?

}