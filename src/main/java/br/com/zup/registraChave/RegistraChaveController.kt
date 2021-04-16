package br.com.zup.registraChave

import br.com.zup.PixRegistraChaveGrpcServiceGrpc
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import javax.inject.Inject

@Controller("/api/chaves")
class RegistraChaveController(
    @Inject val grpcClient: PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub
) {

    @Post
    fun registra(@Body form: RegistraChaveRequest): RegistraChaveResponse {

        val request = form.toModel()

        try {

            val response = grpcClient.registra(request)
//            RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId)

            return RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId)

        }catch (e: StatusRuntimeException) {

            val statusCode = e.status.code
            val description = e.status.description

            if(statusCode == Status.Code.INVALID_ARGUMENT) {
                throw HttpStatusException(HttpStatus.BAD_REQUEST, description)
            }else if(statusCode == Status.Code.ALREADY_EXISTS) {
                throw HttpStatusException(HttpStatus.UNPROCESSABLE_ENTITY, description)
            }

            throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)

        }

    }

}