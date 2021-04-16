package br.com.zup.pix

import br.com.zup.PixRegistraChaveGrpcServiceGrpc
import br.com.zup.PixRemoveChaveGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun registraClientStub(@GrpcChannel("localhost:50051") channel: ManagedChannel):
            PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub? {

        return PixRegistraChaveGrpcServiceGrpc.newBlockingStub(channel)

    }

    @Singleton
    fun removeClienteStub(@GrpcChannel("localhost:50051") channel: ManagedChannel) :
            PixRemoveChaveGrpcServiceGrpc.PixRemoveChaveGrpcServiceBlockingStub? {

        return PixRemoveChaveGrpcServiceGrpc.newBlockingStub(channel)

    }

}