package br.com.samuka182.feirasp.controller

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.PesquisaFeiraParametros
import br.com.samuka182.feirasp.domain.RetornoDto
import br.com.samuka182.feirasp.service.FeiraService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/feira", produces = ["application/json"])
class FeiraController(val feiraService: FeiraService) {

    @PostMapping
    fun salvar(@RequestBody body: FeiraLivreDto) =
        ResponseEntity<FeiraLivreDto>(feiraService.salvarFeira(body), HttpStatus.CREATED)

    @PatchMapping
    fun atualizar(@RequestBody body: FeiraLivreDto) =
        ResponseEntity<FeiraLivreDto>(feiraService.atualizarFeira(body), HttpStatus.OK)

    @GetMapping
    fun pesquisa(
        @RequestParam distrito: String? = null,
        @RequestParam regiao5: String? = null,
        @RequestParam(name = "nome_feira") nomeFeira: String? = null,
        @RequestParam bairro: String? = null
    ) = ResponseEntity<List<FeiraLivreDto>>(
        feiraService.pesquisarFeira(
            PesquisaFeiraParametros(
                nome = nomeFeira, bairro = bairro, regiao5 = regiao5, codDistrito = distrito
            )
        ), HttpStatus.OK
    )

    @DeleteMapping("/{registro}")
    fun delete(@PathVariable registro: String) =
        ResponseEntity<RetornoDto>(feiraService.deletarFeira(registro), HttpStatus.OK)

}