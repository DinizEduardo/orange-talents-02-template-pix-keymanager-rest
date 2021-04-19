package br.com.zup.pix.detalhesChave

import br.com.zup.ConsultaChavePixResponse
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DetalhesChaveResponse(chaveResponse: ConsultaChavePixResponse) {

    val idPix: String = chaveResponse.pixId
    val idCliente = chaveResponse.clienteId
    val chaveInfo = ChavePixInfo(chaveResponse.chave)

}

class ChavePixInfo(info: ConsultaChavePixResponse.ChavePix) {

    val tipoChave = when (info.tipoChave) {
        TipoChave.ALEATORIA -> "ALEATORIA"
        TipoChave.EMAIL -> "EMAIL"
        TipoChave.CELULAR -> "CELULAR"
        TipoChave.CPF -> "CPF"
        else -> "DESCONHECIDA"
    }

    val chavePix = info.chave
    val conta = ContaResponse(info.conta)

    val criadoEm: LocalDateTime = info.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

}

class ContaResponse(conta: ConsultaChavePixResponse.ChavePix.InfoConta) {

    val instituicao = conta.instituicao
    val nomeTitular = conta.nomeTitular
    val cpfTitular = conta.cpfTitular
    val tipoConta = when(conta.tipoConta) {
        TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        else -> "DESCONHECIDA"
    }
    val agencia = conta.agencia
    val numero = conta.numero

}
