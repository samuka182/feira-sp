package br.com.samuka182.feirasp.controller

import br.com.samuka182.feirasp.api.FeiraApi
import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.MensagemRetorno
import br.com.samuka182.feirasp.domain.PesquisaFeiraParametros
import br.com.samuka182.feirasp.service.FeiraService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/feira", produces = ["application/json"])
class FeiraController(
    val feiraService: FeiraService
) : FeiraApi {

    @PostMapping
    override fun salvar(@RequestBody body: FeiraLivreDto) =
        ResponseEntity<FeiraLivreDto>(feiraService.salvarFeira(body), HttpStatus.CREATED)

    @PatchMapping
    override fun atualizar(@RequestBody body: FeiraLivreDto) =
        ResponseEntity<FeiraLivreDto>(feiraService.atualizarFeira(body), HttpStatus.OK)

    @GetMapping
    override fun pesquisa(
        @RequestParam distrito: String?,
        @RequestParam regiao5: String?,
        @RequestParam(name = "nome_feira") nomeFeira: String?,
        @RequestParam bairro: String?,
        @RequestParam(required = false, defaultValue = "50") size: Int?,
        @RequestParam(required = false, defaultValue = "0") page: Int?
    ): ResponseEntity<Page<FeiraLivreDto>> = ResponseEntity(
        feiraService.pesquisarFeira(
            PesquisaFeiraParametros(
                nome = nomeFeira, bairro = bairro, regiao5 = regiao5, codDistrito = distrito
            ), PageRequest.of(page!!, size!!)
        ), HttpStatus.OK
    )

    @DeleteMapping("/{registro}")
    override fun delete(@PathVariable registro: String) =
        ResponseEntity<MensagemRetorno>(feiraService.deletarFeira(registro), HttpStatus.OK)

}