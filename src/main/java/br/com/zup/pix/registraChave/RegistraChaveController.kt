package br.com.zup.pix.registraChave

import br.com.zup.PixRegistraChaveGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/chaves")
class RegistraChaveController(
    @Inject val grpcClient: PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub
) {

    @Post
    fun registra(@Valid @Body form: RegistraChaveRequest): HttpResponse<Any> {

        val request = form.toModel()

        val response = grpcClient.registra(request)
//            RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId)

        return HttpResponse.created(RegistraChaveResponse(clienteId = response.clienteId, pixId = response.pixId))


    }

}