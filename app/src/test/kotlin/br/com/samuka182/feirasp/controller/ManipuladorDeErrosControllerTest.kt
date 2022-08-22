package br.com.samuka182.feirasp.controller

import br.com.samuka182.feirasp.exceptions.BusinessException
import br.com.samuka182.feirasp.exceptions.FeiraLivreExistenteException
import br.com.samuka182.feirasp.exceptions.FeiraLivreNaoEncontradaException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ManipuladorDeErrosControllerTest {

    private val manipuladorDeErrosController = ManipuladorDeErrosController()

    @Test
    fun `erro de negocio quando erro - FeiraLivreExistenteException`() {
        val ex = manipuladorDeErrosController.erroNegocio(FeiraLivreExistenteException())
        assertEquals(HttpStatus.BAD_REQUEST, ex.statusCode)
        assertEquals(400, ex.body?.status)
        assertEquals("Feira livre com identificador ou registro existente", ex.body?.mensagem)
    }

    @Test
    fun `erro de negocio quando erro - FeiraLivreNaoEncontradaException`() {
        val ex = manipuladorDeErrosController.erroNegocio(FeiraLivreNaoEncontradaException())
        assertEquals(HttpStatus.BAD_REQUEST, ex.statusCode)
        assertEquals(400, ex.body?.status)
        assertEquals("Feira livre nao encontrada", ex.body?.mensagem)
    }

    @Test
    fun `erro de negocio quando erro - BusinessException`() {
        val ex = manipuladorDeErrosController.erroNegocio(BusinessException("Erro"))
        assertEquals(HttpStatus.BAD_REQUEST, ex.statusCode)
        assertEquals(400, ex.body?.status)
        assertEquals("Erro", ex.body?.mensagem)
    }

    @Test
    fun `erro interno quando erro nao esperado`() {
        val ex = manipuladorDeErrosController.erroInterno(Throwable("Erro"))
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.statusCode)
        assertEquals(500, ex.body?.status)
        assertEquals("Erro", ex.body?.mensagem)
    }

}