package br.com.samuka182.feirasp.controller

import br.com.samuka182.feirasp.domain.MensagemRetorno
import br.com.samuka182.feirasp.exceptions.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ManipuladorDeErrosController {

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun erroNegocio(ex: BusinessException) =
        ResponseEntity<MensagemRetorno>(MensagemRetorno(400, ex.message!!), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun erroInterno(ex: Throwable) =
        ResponseEntity<MensagemRetorno>(MensagemRetorno(500, ex.message!!), HttpStatus.INTERNAL_SERVER_ERROR)
}