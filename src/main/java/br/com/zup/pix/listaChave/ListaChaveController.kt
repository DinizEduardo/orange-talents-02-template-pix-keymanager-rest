package br.com.zup.pix.listaChave

import br.com.zup.ListaChavePixRequest
import br.com.zup.PixListaChavesPorClienteGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller("/api/clientes")
class ListaChaveController(
    @Inject val grpcLista: PixListaChavesPorClienteGrpcServiceGrpc.PixListaChavesPorClienteGrpcServiceBlockingStub
) {

    @Get("/{clienteId}")
    fun lista(clienteId: UUID) : HttpResponse<Any> {

        val lista = grpcLista.lista(
            ListaChavePixRequest.newBuilder()
                .setClienteId(clienteId.toString())
                .build()
        )

        val response = lista.chavesList.map {
            ChavePixResponse(it)
        }

        return HttpResponse.ok(response)

    }

}