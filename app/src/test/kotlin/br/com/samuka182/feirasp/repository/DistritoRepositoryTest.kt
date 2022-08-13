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
            assertNotNull(distritoRepository.findByIdentificadorExterno("100"))
        }
    }

    @Test
    fun `pesquisa distrito por identificador externo inexistente`() =
        assertNull(distritoRepository.findByIdentificadorExterno("111"))

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
        distritoRepository.save(gerarDistrito()).let { distrito ->
            val copia = Distrito().apply {
                id = distrito.id
                nome = distrito.nome
                identificadorExterno = distrito.identificadorExterno
                criadoEm = distrito.criadoEm
                atualizadoEm = distrito.atualizadoEm
            }
            distrito.nome = "DISTRITO ATUALIZADO"
            distritoRepository.save(distrito).let {
                assertEquals(copia.id, it.id)
                assertNotEquals(copia.nome, it.nome)
                assertEquals(copia.identificadorExterno, it.identificadorExterno)
                assertEquals(copia.criadoEm, it.criadoEm)
                //assertNotEquals(copia.atualizadoEm, it.atualizadoEm)
            }
        }
    }

    private fun gerarDistrito(): Distrito = Distrito("DISTRITO TESTE", "100")

}