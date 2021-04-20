package br.com.zup.pix.compartilhado.annotations

import br.com.zup.pix.registraChave.RegistraChaveRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Constraint(validatedBy = [ChavePixValidaValidator::class])
annotation class ChavePixValida(
    val message: String = "Chave pix inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ChavePixValidaValidator : ConstraintValidator<ChavePixValida, RegistraChaveRequest> {

    override fun isValid(value: RegistraChaveRequest?,
                         annotationMetadata: AnnotationValue<ChavePixValida>,
                         context: ConstraintValidatorContext): Boolean {

        return value!!.tipoChave.valida(value.chave)
    }
}