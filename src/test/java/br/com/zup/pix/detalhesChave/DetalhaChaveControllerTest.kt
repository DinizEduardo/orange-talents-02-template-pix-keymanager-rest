package br.com.zup.pix.detalhesChave

import br.com.zup.*
import br.com.zup.pix.GrpcClientFactory
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DetalhaChaveControllerTest() {

    @field:Inject
    lateinit var consultaGrpc: PixConsultaChaveGrpcServiceGrpc.PixConsultaChaveGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private fun carregaChavePixResponse(
        clienteId: String,
        pixId: String,
        tipoChave: TipoChave,
        chave: String,
        conta: TipoConta
    ): ConsultaChavePixResponse? {

        return ConsultaChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .setChave(
                ConsultaChavePixResponse.ChavePix
                    .newBuilder()
                    .setTipoChave(tipoChave)
                    .setChave(chave)
                    .setConta(
                        ConsultaChavePixResponse.ChavePix.InfoConta
                            .newBuilder()
                            .setTipoConta(conta)
                            .setInstituicao("ITAÚ UNIBANCO S.A.")
                            .setNomeTitular("Eduardo Diniz")
                            .setCpfTitular("48948943863")
                            .setAgencia("0001")
                            .setNumero("321321")
                            .build()
                    )
                    .build()
            )
            .build()

    }

    @Test
    fun `deveria buscar os detalhes da chave pix do tipo email`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val comparacao = ConsultaChavePixRequest.newBuilder()
            .setPixId(
                ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            )
            .build()


        given(consultaGrpc.pesquisa(comparacao)).willReturn(
            carregaChavePixResponse(
                clienteId,
                pixId,
                TipoChave.EMAIL,
                "eduardo@gmail.com",
                TipoConta.CONTA_CORRENTE
            )
        )

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }

    @Test
    fun `deveria buscar os detalhes da chave pix do tipo celular`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val comparacao = ConsultaChavePixRequest.newBuilder()
            .setPixId(
                ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            )
            .build()

        given(consultaGrpc.pesquisa(comparacao)).willReturn(
            carregaChavePixResponse(
                clienteId,
                pixId,
                TipoChave.CELULAR,
                "+5511994783995", TipoConta.CONTA_POUPANCA
            )
        )

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }

    @Test
    fun `deveria buscar os detalhes da chave pix do tipo cpf`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val comparacao = ConsultaChavePixRequest.newBuilder()
            .setPixId(
                ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            )
            .build()

        given(consultaGrpc.pesquisa(comparacao)).willReturn(
            carregaChavePixResponse(
                clienteId,
                pixId,
                TipoChave.CPF,
                "48948943863", TipoConta.CONTA_CORRENTE
            )
        )

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }

    @Test
    fun `deveria buscar os detalhes da chave pix do tipo aleatorio`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val comparacao = ConsultaChavePixRequest.newBuilder()
            .setPixId(
                ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            )
            .build()

        given(consultaGrpc.pesquisa(comparacao)).willReturn(
            carregaChavePixResponse(
                clienteId,
                pixId,
                TipoChave.ALEATORIA,
                UUID.randomUUID().toString(), TipoConta.CONTA_CORRENTE
            )
        )

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }

    @Test
    fun `nao deveria encontrar os detalhes da chave pix`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val comparacao = ConsultaChavePixRequest.newBuilder()
            .setPixId(
                ConsultaChavePixRequest.FiltroPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            )
            .build()

        given(consultaGrpc.pesquisa(comparacao)).willThrow(StatusRuntimeException(Status.NOT_FOUND))

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}/pix/${pixId}")
        val thrown = org.junit.jupiter.api.assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, Any::class.java)
        }

        assertEquals(HttpStatus.NOT_FOUND, thrown.status)
    }


    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() =
            Mockito.mock(PixConsultaChaveGrpcServiceGrpc.PixConsultaChaveGrpcServiceBlockingStub::class.java)

    }

}