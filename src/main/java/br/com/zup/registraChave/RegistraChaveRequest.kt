package br.com.zup.registraChave

import br.com.zup.RegistraChavePixRequest

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
