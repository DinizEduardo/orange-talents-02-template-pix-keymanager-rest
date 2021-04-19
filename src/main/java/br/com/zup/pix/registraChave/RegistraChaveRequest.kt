package br.com.zup.pix.registraChave

import br.com.zup.RegistraChavePixRequest
import br.com.zup.pix.TipoChave
import br.com.zup.pix.TipoConta
import br.com.zup.pix.compartilhado.annotations.ChavePixValida
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ChavePixValida
@Introspected
class RegistraChaveRequest(
    val clienteId: String,
    @field:NotNull
    val tipoChave: TipoChaveRequest,
    @field:Size(max = 77)
    val chave: String,
    @field:NotNull
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

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {
    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) return false

            val isCpfValido = CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }

            if (!chave.matches("^[0-9]{11}\$".toRegex()) || !isCpfValido) {
                return false
            }

            return true
        }

    },
    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank() || !chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) {
                return false
            }

            return true
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }

            val isEmailValido = EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }

            if (!isEmailValido) {
                return false
            }

            return true
        }

    },
    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?): Boolean {
            return chave.isNullOrBlank()
        }

    };

    abstract fun valida(chave: String?): Boolean
}