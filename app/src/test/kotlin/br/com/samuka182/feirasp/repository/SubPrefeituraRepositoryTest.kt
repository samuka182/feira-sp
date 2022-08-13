package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.SubPrefeitura
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
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
    fun `busca uma sub prefeitura existente pelo identificador externo`() {
        subPrefeituraRepository.save(gerarSubPrefeitura())
        val subPrefeitura = subPrefeituraRepository.findByIdentificadorExterno("100")

        assertNotNull(subPrefeitura)
    }

    @Test
    fun `busca uma sub prefeitura com identificador externo inexistente`() {
        val subPrefeitura = subPrefeituraRepository.findByIdentificadorExterno("111")

        assertNull(subPrefeitura)
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
            .copy("SUB PREFREITURA ATUALIZADA")
        val atualizadoEm = subPrefeituraSalva.atualizadoEm
        val subPrefeituraSAtualizada = subPrefeituraRepository.save(subPrefeituraSalva)

        assertEquals(subPrefeituraSalva.id, subPrefeituraSAtualizada.id)
        assertEquals(subPrefeituraSalva.criadoEm, subPrefeituraSAtualizada.criadoEm)
        assertNotEquals(atualizadoEm, subPrefeituraSAtualizada.atualizadoEm)
        assertEquals("SUB PREFREITURA ATUALIZADA", subPrefeituraSAtualizada.nome)
    }

    private fun gerarSubPrefeitura(): SubPrefeitura = SubPrefeitura("SUB PREFREITURA TESTE", "100")

}