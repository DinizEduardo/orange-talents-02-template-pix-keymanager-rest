package br.com.zup.pix.registraChave

import br.com.zup.RegistraChavePixRequest
import br.com.zup.pix.TipoChave
import br.com.zup.pix.TipoConta

class RegistraChaveRequest(
    val clienteId: String,
    val tipoChave: TipoChave,
    val chave: String,
    val tipoConta: TipoConta
) {
    fun toModel(): RegistraChavePixRequest? {

        return RegistraChavePixRequest.newBuilder()
            .setChave(this.chave)
            .setClienteId(this.clienteId)
            .setTipoConta(br.com.zup.TipoConta.valueOf(this.tipoConta.name))
            .setTipoChave(br.com.zup.TipoChave.valueOf(this.tipoChave.name))
            .build()

    }
}
