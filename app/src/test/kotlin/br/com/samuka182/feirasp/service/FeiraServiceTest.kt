package br.com.samuka182.feirasp.service

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import org.junit.jupiter.api.Test
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
        feiraService.salvarFeira(gerarFeiraLivreDto())
    }

    private fun gerarFeiraLivreDto(): FeiraLivreDto = FeiraLivreDto(
        nome = "VILA FORMOSA",
        id = "1",
        codDistrito = "87",
        nomeDistrito = "VILA FORMOSA",
        codSubPrefeitura = "26",
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

}