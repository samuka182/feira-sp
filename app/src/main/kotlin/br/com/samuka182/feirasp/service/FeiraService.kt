package br.com.samuka182.feirasp.service

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.PesquisaFeiraParametros
import br.com.samuka182.feirasp.entities.Distrito
import br.com.samuka182.feirasp.entities.FeiraLivre
import br.com.samuka182.feirasp.entities.SubPrefeitura
import br.com.samuka182.feirasp.repository.DistritoRepository
import br.com.samuka182.feirasp.repository.FeiraLivreRepository
import br.com.samuka182.feirasp.repository.SubPrefeituraRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class FeiraService(
    val feiraLivreRepository: FeiraLivreRepository,
    val distritoRepository: DistritoRepository,
    val subPrefeituraRepository: SubPrefeituraRepository
) {

    @Transactional
    fun salvarFeira(feiraLivreDto: FeiraLivreDto) : FeiraLivreDto =
        salvaOuAtualizaFeira(feiraLivreDto.converteParaEntidade()).converteParaDto()

    @Transactional
    fun atualizarFeira(feiraLivreDto: FeiraLivreDto) = salvaOuAtualizaFeira(feiraLivreDto.converteParaEntidade())

    private fun salvaOuAtualizaFeira(feiraLivre: FeiraLivre) : FeiraLivre {
        feiraLivre.validaDistrito()
        feiraLivre.validaSubPrefeitura()
        feiraLivreRepository.findByIdentificadorExterno(feiraLivre.identificadorExterno!!).let { feiraBase ->
            if (feiraBase != null) {
                feiraBase.nome = feiraLivre.nome
                feiraBase.distrito = feiraLivre.distrito
                feiraBase.subPrefeitura = feiraLivre.subPrefeitura
                feiraBase.regiao5 = feiraLivre.regiao5
                feiraBase.bairro = feiraLivre.bairro

                return feiraLivreRepository.save(feiraBase).also {
                    println(it.id)
                }
            } else {
                return feiraLivreRepository.save(feiraLivre).also {
                    println(it.id)
                }
            }
        }
    }

    private fun FeiraLivre.validaDistrito() {
        distritoRepository.findByIdentificadorExterno(distrito?.identificadorExterno!!).let { distritoBase ->
                if (distritoBase != null) {
                    distritoBase.nome = distrito?.nome!!
                    distritoRepository.save(distritoBase).also {
                        println(it.id)
                    }
                } else {
                    distritoRepository.save(distrito!!).also {
                        println(it.id)
                    }
                }
            }
    }

    private fun FeiraLivre.validaSubPrefeitura() {
        subPrefeituraRepository.findByIdentificadorExterno(subPrefeitura?.identificadorExterno!!)
            .let { subPrefeituraBase ->
                if (subPrefeituraBase != null) {
                    subPrefeituraBase.nome = subPrefeitura?.nome!!
                    subPrefeituraRepository.save(subPrefeituraBase).also {
                        println(it.id)
                    }
                } else {
                    subPrefeituraRepository.save(subPrefeitura!!).also {
                        println(it.id)
                    }
                }
            }
    }

    fun deletarFeira(codRegistro: String) {

    }

    fun pesquisarFeira(parametrosBusca: PesquisaFeiraParametros) {

    }

    private fun FeiraLivreDto.converteParaEntidade(): FeiraLivre {
        return FeiraLivre().apply {
            registro = this@converteParaEntidade.registro
            identificadorExterno = this@converteParaEntidade.id
            nome = this@converteParaEntidade.nome
            distrito = this@converteParaEntidade.converteParaEntidadeDistrito()
            subPrefeitura = this@converteParaEntidade.converteParaEntidadeSubPrefeitura()
            regiao5 = this@converteParaEntidade.regiao5
            bairro = this@converteParaEntidade.bairro
        }
    }

    private fun FeiraLivre.converteParaDto(): FeiraLivreDto {
        return FeiraLivreDto(
            registro = this@converteParaDto.registro,
            id = this@converteParaDto.identificadorExterno,
            nome = this@converteParaDto.nome,
            codDistrito = this@converteParaDto.distrito?.identificadorExterno!!,
            nomeDistrito = this@converteParaDto.distrito?.nome,
            codSubPrefeitura = this@converteParaDto.subPrefeitura?.identificadorExterno,
            nomeSubPrefeitura = this@converteParaDto.subPrefeitura?.nome,
            regiao5 = this@converteParaDto.regiao5,
            bairro = this@converteParaDto.bairro,
        )
    }

    private fun FeiraLivreDto.converteParaEntidadeDistrito(): Distrito {
        return Distrito(nomeDistrito!!, codDistrito)
    }

    private fun FeiraLivreDto.converteParaEntidadeSubPrefeitura(): SubPrefeitura {
        return SubPrefeitura(nomeSubPrefeitura!!, codSubPrefeitura)
    }

}