package br.com.zup.pix.registraChave

import br.com.zup.PixRegistraChaveGrpcServiceGrpc
import br.com.zup.RegistraChavePixRequest
import br.com.zup.RegistraChavePixResponse
import br.com.zup.pix.GrpcClientFactory
import br.com.zup.pix.TipoChave
import br.com.zup.pix.TipoChave.EMAIL
import br.com.zup.pix.TipoConta
import br.com.zup.pix.TipoConta.CONTA_CORRENTE
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChaveControllerTest() {

    @field:Inject
    lateinit var registraStub: PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deveria registrar uma nova chave`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RegistraChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

        given(registraStub.registra(Mockito.any())).willReturn(respostaGrpc)

        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.EMAIL,
            tipoConta = CONTA_CORRENTE,
            chave = "eduardo@zup.com.br"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val response = client.toBlocking().exchange(request, RegistraChaveResponse::class.java)

        assertEquals(HttpStatus.CREATED, response.status)

    }

    @Test
    fun `nao deveria registrar chave duplicada`() {
        val clienteId = UUID.randomUUID().toString()

        given(
            registraStub.registra(
                RegistraChavePixRequest.newBuilder()
                    .setClienteId(clienteId)
                    .setTipoChave(br.com.zup.TipoChave.EMAIL)
                    .setTipoConta(br.com.zup.TipoConta.CONTA_CORRENTE)
                    .setChave("eduardo@zup.com.br")
                    .build()
            )
        ).willThrow(StatusRuntimeException(Status.ALREADY_EXISTS))

        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.EMAIL,
            tipoConta = CONTA_CORRENTE,
            chave = "eduardo@zup.com.br"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, thrown.status)

    }

    @Test
    fun `nao deveria registrar com email invalido`() {
        val clienteId = UUID.randomUUID().toString()


        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.EMAIL,
            tipoConta = CONTA_CORRENTE,
            chave = "eduardo"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)

    }

    @Test
    fun `nao deveria registrar com cpf invalido`() {
        val clienteId = UUID.randomUUID().toString()


        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.CPF,
            tipoConta = CONTA_CORRENTE,
            chave = "489489438"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)

    }

    @Test
    fun `nao deveria registrar com celular invalido`() {
        val clienteId = UUID.randomUUID().toString()


        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.CELULAR,
            tipoConta = CONTA_CORRENTE,
            chave = "321321321"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)

    }

    @Test
    fun `nao deveria registrar com aleatorio invalido`() {
        val clienteId = UUID.randomUUID().toString()


        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.ALEATORIA,
            tipoConta = CONTA_CORRENTE,
            chave = "321321321"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, thrown.status)

    }

    @Test
    fun `nao deveria registrar com um erro no grpc`() {
        val clienteId = UUID.randomUUID().toString()

        given(
            registraStub.registra(
                RegistraChavePixRequest.newBuilder()
                    .setClienteId(clienteId)
                    .setTipoChave(br.com.zup.TipoChave.EMAIL)
                    .setTipoConta(br.com.zup.TipoConta.CONTA_CORRENTE)
                    .setChave("eduardo@zup.com.br")
                    .build()
            )
        ).willThrow(StatusRuntimeException(Status.INTERNAL))

        val novaChave = RegistraChaveRequest(
            clienteId = clienteId,
            tipoChave = TipoChaveRequest.EMAIL,
            tipoConta = CONTA_CORRENTE,
            chave = "eduardo@zup.com.br"
        )

        val request = HttpRequest.POST("/api/chaves", novaChave)
        val thrown = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, RegistraChaveResponse::class.java)
        }

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, thrown.status)

    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() =
            Mockito.mock(PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub::class.java)

    }

}
