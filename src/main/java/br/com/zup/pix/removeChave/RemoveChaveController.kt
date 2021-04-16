package br.com.zup.pix.removeChave

import br.com.zup.PixRemoveChaveGrpcServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import java.util.*
import javax.inject.Inject

@Controller("/api/clientes")
class RemoveChaveController(
    @Inject val grpcRemove: PixRemoveChaveGrpcServiceGrpc.PixRemoveChaveGrpcServiceBlockingStub
) {


    @Delete("/{clienteId}/chaves/{pixId}")
    fun remove(
        clienteId: UUID,
        pixId: UUID
    ) : UUID {

        grpcRemove.remove(RemoveChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()
        )

        return pixId
    }

}