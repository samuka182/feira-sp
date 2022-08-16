package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.Distrito
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DistritoRepositoryTest {

    @Autowired
    private lateinit var distritoRepository: DistritoRepository

    @Test
    fun `salva novo distrito com sucesso`() = assertDoesNotThrow {
        distritoRepository.save(gerarDistrito()).let { distrito ->
            assertNotNull(distrito.id)
            assertNotNull(distrito.criadoEm)
            assertNotNull(distrito.atualizadoEm)
        }
    }

    @Test
    fun `pesquisa distrito por identificador externo`() = assertDoesNotThrow {
        distritoRepository.save(gerarDistrito()).also {
            assertTrue(distritoRepository.findById(100).isPresent)
        }
    }

    @Test
    fun `pesquisa distrito por identificador externo inexistente`() =
        assertFalse(distritoRepository.findById(111).isPresent)

    @Test
    fun `deleta um distrito com sucesso`() = assertDoesNotThrow {
        distritoRepository.save(gerarDistrito()).let { distrito ->
            distritoRepository.delete(distrito)

            assertThrows<NoSuchElementException> {
                distritoRepository.findById(distrito.id!!).get()
            }
        }
    }

    @Test
    fun `atualiza dados de distrito`() = assertDoesNotThrow {
        distritoRepository.save(gerarDistrito()).let { distritoSalvo ->
            val atualizadoEm = distritoSalvo.atualizadoEm
            distritoRepository.save(distritoSalvo.copy(nome = "DISTRITO ATUALIZADO")).let { distritoAtualizado ->
                assertEquals(distritoSalvo.id, distritoAtualizado.id)
                assertEquals("DISTRITO ATUALIZADO", distritoAtualizado.nome)
                assertEquals(distritoSalvo.criadoEm, distritoAtualizado.criadoEm)
                assertNotEquals(atualizadoEm, distritoAtualizado.atualizadoEm)
            }
        }
    }

    private fun gerarDistrito(): Distrito = Distrito(
        nome = "DISTRITO TESTE", id = 100
    )

}