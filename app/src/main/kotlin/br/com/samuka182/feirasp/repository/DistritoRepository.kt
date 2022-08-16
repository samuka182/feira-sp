package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.Distrito
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DistritoRepository : JpaRepository<Distrito, Int>