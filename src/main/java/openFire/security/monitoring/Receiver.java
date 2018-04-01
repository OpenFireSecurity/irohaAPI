package openFire.security.monitoring;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import iroha.protocol.BlockOuterClass;
import iroha.protocol.Queries;
import iroha.protocol.QueryServiceGrpc;
import openFire.security.monitoring.model.SensorTransaction;
import openFire.security.monitoring.model.VerifierTransaction;
import openFire.security.monitoring.model.sensorResponse.SensorStatusMap;
import openFire.security.monitoring.parser.SensorStatusParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public SensorStatusMap getUniqueSensorUpdates(String sensorId) {
        logger.info("Will try to get verifier updates");

        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAccountDetail(Queries.GetAccountDetail.newBuilder()
                                .setAccountId(sensorId)
                                .build()))
                .build();
        iroha.protocol.Responses.QueryResponse queryResponse = blockingStub.find(query);

        try {
            return SensorStatusParser.parse(queryResponse.getAccountDetailResponse().getDetail());
        } catch (Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<SensorTransaction> getAllSensorUpdates() {
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

        List<SensorTransaction> sensorTransactions = new ArrayList<>();
        try {
            for (BlockOuterClass.Transaction transaction : queryResponse.getTransactionsResponse().getTransactionsList()) {
                SensorTransaction sensorTransaction = null;
                try {
                    sensorTransaction = new SensorTransaction(transaction);
                } catch (Exception e) {
                    logger.warning("Transaction can't be parsed");
                }
                if (sensorTransaction != null) {
                    sensorTransactions.add(sensorTransaction);
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
        return sensorTransactions.stream().sorted(Comparator.comparing(SensorTransaction::getLocalDateTime).reversed()).collect(Collectors.toList());
    }


    public List<VerifierTransaction> getAllVerifierUpdates(String verifierId) {
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

        List<VerifierTransaction> verifierTransactions = new ArrayList<>();
        try {
            for (BlockOuterClass.Transaction transaction : queryResponse.getTransactionsResponse().getTransactionsList()) {
                VerifierTransaction verifierTransaction = null;
                try {
                    verifierTransaction = new VerifierTransaction(transaction);
                } catch (Exception e) {
                    logger.warning("Transaction can't be parsed");
                }
                if (verifierTransaction != null) {
                    verifierTransactions.add(verifierTransaction);
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }

        return verifierTransactions.stream().sorted(Comparator.comparing(VerifierTransaction::getLocalDateTime).reversed()).collect(Collectors.toList());
    }
}
