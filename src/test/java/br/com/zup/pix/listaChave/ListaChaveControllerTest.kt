package br.com.zup.pix.listaChave

import br.com.zup.*
import br.com.zup.pix.GrpcClientFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChaveControllerTest() {

    @field:Inject
    lateinit var listaStub: PixListaChavesPorClienteGrpcServiceGrpc.PixListaChavesPorClienteGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deveria listar as chaves do cliente `() {
        val clienteId = UUID.randomUUID().toString()

        val respostaGrpc = listaChavePixResponse(clienteId)

        given(
            listaStub.lista(
                ListaChavePixRequest.newBuilder()
                    .setClienteId(clienteId).build()
            )
        ).willReturn(respostaGrpc)

        val request = HttpRequest.GET<Any>("/api/clientes/${clienteId}")
        val response = client.toBlocking().exchange(request, List::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(response.body()!!.size, 5)
    }

    private fun listaChavePixResponse(
        clienteId: String
    ): ListaChavePixResponse? {

        return ListaChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .addChaves(criaChave(TipoChave.CPF, "48948943863", TipoConta.CONTA_CORRENTE))
            .addChaves(criaChave(TipoChave.ALEATORIA, UUID.randomUUID().toString(), TipoConta.CONTA_POUPANCA))
            .addChaves(criaChave(TipoChave.EMAIL, "eduardo@gmail.com", TipoConta.CONTA_CORRENTE))
            .addChaves(criaChave(TipoChave.CELULAR, "+5511994783995", TipoConta.CONTA_POUPANCA))
            .addChaves(criaChave(TipoChave.DESCONHECIDO, "ofuhsdiudhofhsdufsdf", TipoConta.DESCONHECIDA))
            .build()

    }

    fun criaChave(tipoChave: TipoChave, chave: String, tipoConta: TipoConta): ListaChavePixResponse.Chave? {
        return ListaChavePixResponse.Chave.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoChave(tipoChave)
            .setChave(chave)
            .setTipoConta(tipoConta)
            .setCriadaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()

                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()
    }


    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() =
            Mockito.mock(PixListaChavesPorClienteGrpcServiceGrpc.PixListaChavesPorClienteGrpcServiceBlockingStub::class.java)

    }

}