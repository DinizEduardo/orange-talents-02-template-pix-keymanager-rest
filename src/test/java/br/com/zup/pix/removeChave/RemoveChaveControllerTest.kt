package br.com.zup.pix.removeChave

import br.com.zup.*
import br.com.zup.pix.GrpcClientFactory
import br.com.zup.pix.TipoConta
import br.com.zup.pix.registraChave.RegistraChaveRequest
import br.com.zup.pix.registraChave.RegistraChaveResponse
import br.com.zup.pix.registraChave.TipoChaveRequest
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoveChaveControllerTest() {

    @field:Inject
    lateinit var removeStub: PixRemoveChaveGrpcServiceGrpc.PixRemoveChaveGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deveria excluir uma chave`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RemoveChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

        given(removeStub.remove(Mockito.any())).willReturn(respostaGrpc)

        val request = HttpRequest.DELETE<Any>("/api/clientes/${clienteId}/chaves/${pixId}")
        val response = client.toBlocking().exchange(request, RemoveChavePixResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(PixRemoveChaveGrpcServiceGrpc.PixRemoveChaveGrpcServiceBlockingStub::class.java)

    }

}