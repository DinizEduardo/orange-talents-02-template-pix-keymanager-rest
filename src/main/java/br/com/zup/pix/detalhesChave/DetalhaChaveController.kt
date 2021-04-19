package br.com.zup.pix.detalhesChave

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.PixConsultaChaveGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller("/api")
class DetalhaChaveController(
    @Inject val grpcConsulta: PixConsultaChaveGrpcServiceGrpc.PixConsultaChaveGrpcServiceBlockingStub
) {

    // procura pelo clienteId e pelo id do pix
    @Get("/clientes/{clienteId}/pix/{pixId}")
    fun detalhamento(
        clienteId: UUID,
        pixId: UUID
    ): HttpResponse<Any> {

        val chaveResponse = grpcConsulta.pesquisa(
            ConsultaChavePixRequest.newBuilder()
                .setPixId(ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setPixId(pixId.toString())
                    .setClienteId(clienteId.toString())
                    .build()
                )
                .build()
        )

        return HttpResponse.ok(DetalhesChaveResponse(chaveResponse))

    }

}