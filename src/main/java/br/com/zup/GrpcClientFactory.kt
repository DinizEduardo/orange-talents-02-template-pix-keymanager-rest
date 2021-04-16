package br.com.zup

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun registraClientStub(@GrpcChannel("registra") channel: ManagedChannel):
            PixRegistraChaveGrpcServiceGrpc.PixRegistraChaveGrpcServiceBlockingStub? {

        return PixRegistraChaveGrpcServiceGrpc.newBlockingStub(channel)

    }

}