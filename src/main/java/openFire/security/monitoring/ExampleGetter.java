package openFire.security.monitoring;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import iroha.protocol.*;

public class ExampleGetter {
    private static final Logger logger = Logger.getLogger(ExampleGetter.class.getName());

    private final ManagedChannel channel;
    private final QueryServiceGrpc.QueryServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public ExampleGetter(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    ExampleGetter(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = QueryServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void getChecks() {
        logger.info("Will try to get checks");
        Queries.Query query = Queries.Query.newBuilder().setPayload(
                Queries.Query.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setQueryCounter(1l)
                        .setCreatorAccountId("observer@test")
                        .setGetAccountDetail(Queries.GetAccountDetail.newBuilder()
                                .setAccountId("sensorid@test")
                                .build()))
                .build();

        iroha.protocol.Responses.QueryResponse queryResponse= blockingStub.find(query);
        System.out.println(queryResponse);

    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        ExampleGetter client = new ExampleGetter("192.168.1.227", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.getChecks();
        } finally {
            client.shutdown();
        }
    }
}