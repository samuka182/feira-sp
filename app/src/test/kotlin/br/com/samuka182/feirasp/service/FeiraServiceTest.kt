package br.com.samuka182.feirasp.service

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.PesquisaFeiraParametros
import br.com.samuka182.feirasp.exceptions.BusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FeiraServiceTest {

    @Autowired
    private lateinit var feiraService: FeiraService

    @Test
    fun `salvar nova feira livre`() {
        assertDoesNotThrow {
            salvarFeira(gerarFeiraLivreDto())
        }
    }

    @Test
    fun `pesquisa feiras salvas`() {
        salvarMultiplasFeiras()
        assertEquals(2, feiraService.pesquisarFeira(PesquisaFeiraParametros(codDistrito = "87")).size)
        assertEquals(2, feiraService.pesquisarFeira(PesquisaFeiraParametros(bairro = "VL FORMOSA")).size)
        assertEquals(
            1,
            feiraService.pesquisarFeira(PesquisaFeiraParametros(bairro = "VL FORMOSA", nome = "VILA FORMOSA")).size
        )
    }

    @Test
    fun `atualizar distrito de uma feira livre existente`() {
        salvarFeira(gerarFeiraLivreDto())

        feiraService.atualizarFeira(FeiraLivreDto(id = 1, codDistrito = 99, nomeDistrito = "DISTRITO TESTE")).let {
            assertEquals(1, it.id)
            assertEquals("DISTRITO TESTE", it.nomeDistrito)
            assertEquals(99, it.codDistrito)
        }
    }

    @Test
    fun `atualizar sub prefeitura de uma feira livre existente`() {
        salvarFeira(gerarFeiraLivreDto())

        feiraService.atualizarFeira(
            FeiraLivreDto(
                id = 1,
                codSubPrefeitura = 99,
                nomeSubPrefeitura = "SUB PREFEITURA TESTE"
            )
        ).let {
            assertEquals(1, it.id)
            assertEquals("SUB PREFEITURA TESTE", it.nomeSubPrefeitura)
            assertEquals(99, it.codSubPrefeitura)
        }
    }

    @Test
    fun `erro BusinessException ao atualizar uma feira livre com identificador inexistente`() {
        assertThrows<BusinessException> {
            feiraService.atualizarFeira(FeiraLivreDto(id = 999, nome = "TESTE ALTERAR"))
        }
    }

    @Test
    fun `erro BusinessException ao atualizar registro de uma feira livre existente`() {
        salvarFeira(gerarFeiraLivreDto())

        assertThrows<BusinessException> {
            feiraService.atualizarFeira(FeiraLivreDto(id = 1, registro = "1234-5"))
        }
    }

    @Test
    fun `sucesso ao deletar uma feira com registro existente`() {
        salvarFeira(gerarFeiraLivreDto())

        assertDoesNotThrow{
            feiraService.deletarFeira("4041-0")
        }
    }

    @Test
    fun `erro BusinessException ao deletar uma feira com registro inexistente`() {
        salvarFeira(gerarFeiraLivreDto())

        assertThrows<BusinessException> {
            feiraService.deletarFeira("9999-9")
        }
    }

    private fun salvarFeira(feiraLivreDto: FeiraLivreDto) = feiraService.salvarFeira(feiraLivreDto)

    private fun salvarMultiplasFeiras() {
        salvarFeira(gerarFeiraLivreDto())
        salvarFeira(gerarFeiraLivre2Dto())
    }

    private fun gerarFeiraLivreDto(): FeiraLivreDto = FeiraLivreDto(
        nome = "VILA FORMOSA",
        id = 1,
        codDistrito = 87,
        nomeDistrito = "VILA FORMOSA",
        codSubPrefeitura = 26,
        nomeSubPrefeitura = "ARICANDUVA-FORMOSA-CARRAO",
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

    private fun gerarFeiraLivre2Dto(): FeiraLivreDto = FeiraLivreDto(
        nome = "VILA FORMOSA 2",
        id = 2,
        codDistrito = 87,
        nomeDistrito = "VILA FORMOSA",
        codSubPrefeitura = 28,
        nomeSubPrefeitura = "ARICANDUVA-FORMOSA-CARRAO",
        areaPonderacao = 3550308005040,
        setorCenso = 355030885000091,
        latitude = -23558733,
        longitude = -46550164,
        regiao5 = "Leste",
        regiao8 = "Leste 1",
        registro = "4041-1",
        logradouro = "RUA MARAGOJIPE",
        numero = null,
        bairro = "VL FORMOSA",
        referencia = "TV RUA PRETORIA",
    )

}