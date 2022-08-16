package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.SubPrefeitura
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SubPrefeituraRepositoryTest {

    @Autowired
    private lateinit var subPrefeituraRepository: SubPrefeituraRepository

    @Test
    fun `verifica insercao de nova sub prefeitura`() {
        val subPrefeitura = subPrefeituraRepository.save(gerarSubPrefeitura())

        assertNotNull(subPrefeitura.id)
        assertNotNull(subPrefeitura.criadoEm)
        assertNotNull(subPrefeitura.atualizadoEm)
    }

    @Test
    fun `busca uma sub prefeitura existente pelo identificador`() {
        subPrefeituraRepository.save(gerarSubPrefeitura())
        val subPrefeitura = subPrefeituraRepository.findById(100)

        assertTrue(subPrefeitura.isPresent)
    }

    @Test
    fun `busca uma sub prefeitura com identificador inexistente`() {
        val subPrefeitura = subPrefeituraRepository.findById(111)

        assertFalse(subPrefeitura.isPresent)
    }

    @Test
    fun `verifica delecao de uma sub prefeitura existente`() {
        val subPrefeituraSalva = subPrefeituraRepository.save(gerarSubPrefeitura())
        val subPrefeitura = subPrefeituraRepository.findById(subPrefeituraSalva.id!!).get()
        assertNotNull(subPrefeitura)
        subPrefeituraRepository.delete(subPrefeitura)

        assertThrows<NoSuchElementException> {
            subPrefeituraRepository.findById(subPrefeituraSalva.id!!).get()
        }
    }

    @Test
    fun `atualiza dados de sub prefeitura`() {
        val subPrefeituraSalva = subPrefeituraRepository.save(gerarSubPrefeitura())
        val atualizadoEm = subPrefeituraSalva.atualizadoEm
        val subPrefeituraAtualizada = subPrefeituraRepository.save(subPrefeituraSalva.copy(nome = "SUB PREFREITURA ATUALIZADA"))

        assertEquals(subPrefeituraSalva.id, subPrefeituraAtualizada.id)
        assertEquals(subPrefeituraSalva.criadoEm, subPrefeituraAtualizada.criadoEm)
        assertNotEquals(atualizadoEm, subPrefeituraAtualizada.atualizadoEm)
        assertEquals("SUB PREFREITURA ATUALIZADA", subPrefeituraAtualizada.nome)
    }

    private fun gerarSubPrefeitura(): SubPrefeitura = SubPrefeitura(
        nome = "SUB PREFREITURA TESTE",
        id = 100
    )

}