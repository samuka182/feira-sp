package br.com.samuka182.feirasp.controller

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.service.FeiraService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class FeiraControllerTest {

    private val feiraService = mockk<FeiraService>()
    private val feiraController = FeiraController(feiraService)
    private val feiraDto = FeiraLivreDto(
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

    @Test
    fun `salvar nova feira - sucesso`() {
        every { feiraService.salvarFeira(any()) } returns mockk()

        assertEquals(HttpStatus.CREATED, feiraController.salvar(feiraDto).statusCode)
    }

    @Test
    fun `atualizar nova feira - sucesso`() {
        every { feiraService.atualizarFeira(any()) } returns mockk()

        assertEquals(HttpStatus.OK, feiraController.atualizar(feiraDto).statusCode)
    }

    @Test
    fun `listar feiras - sucesso`() {
        every { feiraService.pesquisarFeira(any()) } returns mockk()

        assertEquals(HttpStatus.OK, feiraController.pesquisa().statusCode)
    }

    @Test
    fun `deletar feira - sucesso`() {
        every { feiraService.deletarFeira(any()) } returns mockk()

        assertEquals(HttpStatus.OK, feiraController.delete("9999-1").statusCode)
    }

}