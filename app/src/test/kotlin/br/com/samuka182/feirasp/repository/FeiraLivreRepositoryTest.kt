package br.com.samuka182.feirasp.repository

import br.com.samuka182.feirasp.entities.Distrito
import br.com.samuka182.feirasp.entities.FeiraLivre
import br.com.samuka182.feirasp.entities.SubPrefeitura
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FeiraLivreRepositoryTest {

    @Autowired
    private lateinit var feiraLivreRepository: FeiraLivreRepository

    @Autowired
    private lateinit var distritoRepository: DistritoRepository

    @Autowired
    private lateinit var subPrefeituraRepository: SubPrefeituraRepository

    @Test
    fun `salvar nova feira com sucesso`() = assertDoesNotThrow {
        feiraLivreRepository.save(gerarFeiraLivre()).let { feiraLivre ->
            assertNotNull(feiraLivre.id)
            assertNotNull(feiraLivre.criadoEm)
            assertNotNull(feiraLivre.atualizadoEm)
        }
    }

    @Test
    fun `pesquisa feira por identificador`() = assertDoesNotThrow {
        feiraLivreRepository.save(gerarFeiraLivre()).also {
            assertTrue(feiraLivreRepository.findById(1).isPresent)
        }
    }

    @Test
    fun `pesquisa feira por identificador inexistente`() =
        assertFalse(feiraLivreRepository.findById(100).isPresent)

    @Test
    fun `atualiza dados de uma feira com sucesso`() = assertDoesNotThrow {
        feiraLivreRepository.save(gerarFeiraLivre()).also {
            feiraLivreRepository.findById(it.id!!).get().let { feira ->
                feira.nome = "FEIRA ATUALIZADA"

                feiraLivreRepository.save(feira).let { feiraAtualizada ->
                    assertEquals("FEIRA ATUALIZADA", feiraAtualizada.nome)
                    assertEquals(feira.id, feiraAtualizada.id)
                    assertEquals(feira.criadoEm, feiraAtualizada.criadoEm)
                }
            }
        }
    }

    @Test
    fun `deleta uma feira com sucesso`() = assertDoesNotThrow {
        feiraLivreRepository.save(gerarFeiraLivre()).also { feira ->
            feiraLivreRepository.delete(feira)

            assertThrows<NoSuchElementException> {
                feiraLivreRepository.findById(feira.id!!).get()
            }
        }
    }

    private fun gerarFeiraLivre(): FeiraLivre = FeiraLivre(
        id = 1,
        nome = "VILA FORMOSA",
        distrito = salvarDistrito(),
        subPrefeitura = salvarSubPrefeitura(),
        areaPonderacao = 3550308005040,
        setorCenso = 355030885000091,
        latitude = -23558733,
        longitude = -46550164,
        regiao5 = "Leste",
        regiao8 = "Leste 1",
        registro = "4041-0",
        logradouro = "RUA MARAGOJIPE",
        numero = null,
        bairro = "VL FORMOSA",
        referencia = "TV RUA PRETORIA",
    )

    private fun salvarDistrito(): Distrito =
        distritoRepository.save(Distrito(id = 87, nome = "VILA FORMOSA"))

    private fun salvarSubPrefeitura(): SubPrefeitura =
        subPrefeituraRepository.save(SubPrefeitura(id = 26, nome = "ARICANDUVA-FORMOSA-CARRAO"))
}

