package br.com.zup.pix.listaChave

import br.com.zup.ListaChavePixResponse
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ChavePixResponse(chaveResponse: ListaChavePixResponse.Chave) {

    val id = chaveResponse.pixId
    val tipoChave = when(chaveResponse.tipoChave) {
        TipoChave.CPF -> "CPF"
        TipoChave.CELULAR -> "CELULAR"
        TipoChave.EMAIL -> "EMAIL"
        TipoChave.ALEATORIA -> "ALEATORIA"
        else -> "DESCONHECIDA"
    }

    val chave = chaveResponse.chave
    val tipoConta = when(chaveResponse.tipoConta) {
        TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "DESCONHECIDO"
    }

    val criadaEm = chaveResponse.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

}