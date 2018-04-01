package openFire.security.monitoring;

import com.google.protobuf.Message;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import iroha.protocol.Queries;
import iroha.protocol.QueryServiceGrpc;
import iroha.protocol.Responses;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Receiver {
    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    private final ManagedChannel channel;
    private final QueryServiceGrpc.QueryServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public Receiver(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    Receiver(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = QueryServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Responses.QueryResponse getUniqueSensorUpdates() {
        logger.info("Will try to get verifier updates");

        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAccountDetail(Queries.GetAccountDetail.newBuilder()
                                .setAccountId("sensorid@test")
                                .build()))
                .build();
        iroha.protocol.Responses.QueryResponse queryResponse = blockingStub.find(query);
        return queryResponse;
    }

    public Responses.QueryResponse getAllSensorUpdates() {
        logger.info("Will try to get verifier updates");
        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAccountTransactions(Queries.GetAccountTransactions.newBuilder()
                                .setAccountId("sensorid@test")
                                .build()))
                .build();

        iroha.protocol.Responses.QueryResponse queryResponse = blockingStub.find(query);
        return queryResponse;
    }


    public Responses.QueryResponse getAllVerifiersUpdates() {
        logger.info("Will try to get verifier updates");

        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAssetInfo(Queries.GetAssetInfo.newBuilder()
                                .setAssetId("checks#test")
                                .build()))
                .build();
        iroha.protocol.Responses.QueryResponse queryResponse = blockingStub.find(query);
        return queryResponse;
    }

    public Responses.QueryResponse getAllVerifierUpdates(String verifierId) {
        logger.info("Will try to get verifier updates");
        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAccountTransactions(Queries.GetAccountTransactions.newBuilder()
                                .setAccountId(verifierId)
                                .build()))
                .build();

        iroha.protocol.Responses.QueryResponse queryResponse = blockingStub.find(query);
        return queryResponse;
    }
}
