package br.com.samuka182.feirasp.service

import br.com.samuka182.feirasp.domain.FeiraLivreDto
import br.com.samuka182.feirasp.domain.PesquisaFeiraParametros
import br.com.samuka182.feirasp.domain.RetornoDto
import br.com.samuka182.feirasp.entities.Distrito
import br.com.samuka182.feirasp.entities.FeiraLivre
import br.com.samuka182.feirasp.entities.SubPrefeitura
import br.com.samuka182.feirasp.exceptions.BusinessException
import br.com.samuka182.feirasp.repository.DistritoRepository
import br.com.samuka182.feirasp.repository.FeiraLivreRepository
import br.com.samuka182.feirasp.repository.SubPrefeituraRepository
import mu.KotlinLogging
import mu.withLoggingContext
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class FeiraService(
    val feiraLivreRepository: FeiraLivreRepository,
    val distritoRepository: DistritoRepository,
    val subPrefeituraRepository: SubPrefeituraRepository
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Transactional
    fun salvarFeira(feiraLivreDto: FeiraLivreDto): FeiraLivreDto =
        withLoggingContext("acao" to "salvar", "id" to feiraLivreDto.id?.toString(), "nome" to feiraLivreDto.nome) {
            logger.info { "Iniciando salvar feira" }
            return salvaOuAtualizaFeira(feiraLivreDto.converteParaEntidade()).converteParaDto().also {
                logger.info { "Finalizando salvar feira" }
            }
        }

    @Transactional
    fun atualizarFeira(feiraLivreDto: FeiraLivreDto): FeiraLivreDto =
        withLoggingContext("acao" to "atualizar", "id" to feiraLivreDto.id?.toString(), "nome" to feiraLivreDto.nome) {
            logger.info { "validando operacao de atualizacao" }
            feiraLivreDto.validaAtualizacaoFeira()
            return salvaOuAtualizaFeira(feiraLivreDto.converteParaEntidade()).converteParaDto().also {
                logger.info { "Finalizando atualizar feira" }
            }
        }

    @Transactional
    fun deletarFeira(codRegistro: String): RetornoDto =
        withLoggingContext("acao" to "deletar", "codRegistro" to codRegistro) {
            logger.info { "iniciando validacao de delecao" }
            feiraLivreRepository.findByRegistro(codRegistro).let { feiraBase ->
                when (feiraBase) {
                    null -> throw BusinessException("Feira nao encontrada pelo codigo de registro")
                    else -> withLoggingContext("id" to feiraBase.id?.toString(), "nome" to feiraBase.nome) {
                        logger.info { "iniciando operacao de delecao" }
                        feiraLivreRepository.delete(feiraBase)
                        logger.info { "finalizando operacao de delecao" }
                        return@let RetornoDto(200, "sucesso")
                    }
                }
            }
        }

    @Transactional
    fun pesquisarFeira(parametrosBusca: PesquisaFeiraParametros): List<FeiraLivreDto> {
        val exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
        return feiraLivreRepository.findAll(Example.of(de(parametrosBusca), exampleMatcher))
            .map { feira -> feira.converteParaDto() }.toList()
    }

    private fun FeiraLivreDto.validaAtualizacaoFeira() {
        try {
            val feiraBase = feiraLivreRepository.findById(this.id!!).get()
            this.registro?.let { if (it != feiraBase.registro) throw BusinessException("Nao e possivel alterar o codigo de registro") }
            logger.info { "operacao validada com sucesso" }
        } catch (ns: NoSuchElementException) {
            throw BusinessException("Feira nao encontrada pelo identificador")
        }
    }

    private fun de(parametrosBusca: PesquisaFeiraParametros): FeiraLivre {
        return FeiraLivre(
            distrito = Distrito(id = parametrosBusca.codDistrito?.toInt()),
            regiao5 = parametrosBusca.regiao5,
            nome = parametrosBusca.nome,
            bairro = parametrosBusca.bairro,
        )
    }

    private fun salvaOuAtualizaFeira(feiraLivre: FeiraLivre): FeiraLivre {
        feiraLivreRepository.findById(feiraLivre.id!!).let { feiraBase ->
            if (feiraBase.isPresent) {
                val feira = feiraBase.get()

                feiraLivre.distrito?.id?.let {
                    feira.distrito = validaDistrito(feiraLivre.distrito)
                }
                feiraLivre.subPrefeitura?.id?.let {
                    feira.subPrefeitura = validaSubPrefeitura(feiraLivre.subPrefeitura)
                }
                feiraLivre.nome?.let { feira.nome = it }
                feiraLivre.regiao5?.let { feira.regiao5 = it }
                feiraLivre.bairro?.let { feira.bairro = it }
                feiraLivre.areaPonderacao?.let { feira.areaPonderacao = it }
                feiraLivre.setorCenso?.let { feira.setorCenso = it }
                feiraLivre.latitude?.let { feira.latitude = it }
                feiraLivre.longitude?.let { feira.longitude = it }
                feiraLivre.regiao8?.let { feira.regiao8 = it }
                feiraLivre.logradouro?.let { feira.logradouro = it }
                feiraLivre.numero?.let { feira.numero = it }
                feiraLivre.referencia?.let { feira.referencia = it }

                return feiraLivreRepository.save(feira)
            } else {
                feiraLivre.distrito = validaDistrito(feiraLivre.distrito)
                feiraLivre.subPrefeitura = validaSubPrefeitura(feiraLivre.subPrefeitura)

                return feiraLivreRepository.save(feiraLivre)
            }
        }
    }

    private fun validaDistrito(distrito: Distrito?): Distrito? {
        distrito?.id?.let {
            val distritoBase = distritoRepository.findById(it)
            return if (distritoBase.isPresent) {
                distritoBase.get().nome = distrito.nome
                distritoRepository.save(distritoBase.get())
            } else {
                distritoRepository.save(distrito)
            }
        }
        return null
    }

    private fun validaSubPrefeitura(subPrefeitura: SubPrefeitura?): SubPrefeitura? {
        subPrefeitura?.id?.let {
            val subPrefeituraBase = subPrefeituraRepository.findById(it)
            return if (subPrefeituraBase.isPresent) {
                subPrefeituraBase.get().nome = subPrefeitura.nome
                subPrefeituraRepository.save(subPrefeituraBase.get())
            } else {
                subPrefeituraRepository.save(subPrefeitura)
            }
        }
        return null
    }

    private fun FeiraLivreDto.converteParaEntidade(): FeiraLivre {
        return FeiraLivre(
            registro = this.registro,
            id = this.id,
            nome = this.nome,
            distrito = this.converteParaEntidadeDistrito(),
            subPrefeitura = this.converteParaEntidadeSubPrefeitura(),
            regiao5 = this.regiao5,
            bairro = this.bairro,
            latitude = this.latitude,
            longitude = this.longitude,
            regiao8 = this.regiao8,
            setorCenso = this.setorCenso,
            areaPonderacao = this.areaPonderacao,
            logradouro = this.logradouro,
            referencia = this.referencia,
            numero = this.numero,
        )
    }

    private fun FeiraLivre.converteParaDto(): FeiraLivreDto {
        return FeiraLivreDto(
            registro = this.registro,
            id = this.id,
            nome = this.nome,
            codDistrito = this.distrito?.id,
            nomeDistrito = this.distrito?.nome,
            codSubPrefeitura = this.subPrefeitura?.id,
            nomeSubPrefeitura = this.subPrefeitura?.nome,
            regiao5 = this.regiao5,
            bairro = this.bairro,
            latitude = this.latitude,
            longitude = this.longitude,
            regiao8 = this.regiao8,
            setorCenso = this.setorCenso,
            areaPonderacao = this.areaPonderacao,
            logradouro = this.logradouro,
            referencia = this.referencia,
            numero = this.numero,
            criadoEm = this.atualizadoEm,
            atualizadoEm = this.criadoEm,
        )
    }

    private fun FeiraLivreDto.converteParaEntidadeDistrito(): Distrito {
        return Distrito(
            nome = nomeDistrito, id = codDistrito
        )
    }

    private fun FeiraLivreDto.converteParaEntidadeSubPrefeitura(): SubPrefeitura {
        return SubPrefeitura(
            nome = nomeSubPrefeitura, id = codSubPrefeitura
        )
    }

}