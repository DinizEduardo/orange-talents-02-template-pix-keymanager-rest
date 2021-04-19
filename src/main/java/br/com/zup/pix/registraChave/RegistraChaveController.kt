package br.com.zup.pix.registraChave

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

        val response = grpcClient.registra(request)
//            RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId)

        return RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId)


    }

}