package br.com.samuka182.feirasp.api

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.MensagemRetorno
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "feirasp-api", description = "Gestao de Feiras Livres da cidade de Sao Paulo")
interface FeiraApi {

    @Operation(summary = "Cadastra uma nova feira livre")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "Cadastrou uma feira livre com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = FeiraLivreDto::class))]
        ), ApiResponse(
            responseCode = "400",
            description = "Requisicao mal feita",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        ), ApiResponse(
            responseCode = "500",
            description = "Erro inesperado",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        )]
    )
    fun salvar(body: FeiraLivreDto): ResponseEntity<FeiraLivreDto>

    @Operation(summary = "Atualiza os dados de uma feira livre")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Atualizou uma feira livre com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = FeiraLivreDto::class))]
        ), ApiResponse(
            responseCode = "400",
            description = "Requisicao mal feita",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        ), ApiResponse(
            responseCode = "500",
            description = "Erro inesperado",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        )]
    )
    fun atualizar(body: FeiraLivreDto): ResponseEntity<FeiraLivreDto>

    @Operation(summary = "Busca feiras livres")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "Feiras livres encontradas com sucesso",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = FeiraLivreDto::class))
            )]
        ), ApiResponse(
            responseCode = "400",
            description = "Requisicao mal feita",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        ), ApiResponse(
            responseCode = "500",
            description = "Erro inesperado",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        )]
    )
    fun pesquisa(
        distrito: String? = null,
        regiao5: String? = null,
        nomeFeira: String? = null,
        bairro: String? = null
    ): ResponseEntity<List<FeiraLivreDto>>

    @Operation(summary = "Deleta registro de feira livre")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Feiras livres removida com sucesso",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = MensagemRetorno::class))
            )]
        ), ApiResponse(
            responseCode = "400",
            description = "Requisicao mal feita",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        ), ApiResponse(
            responseCode = "500",
            description = "Erro inesperado",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = MensagemRetorno::class)
            )]
        )]
    )
    fun delete(registro: String): ResponseEntity<MensagemRetorno>

}