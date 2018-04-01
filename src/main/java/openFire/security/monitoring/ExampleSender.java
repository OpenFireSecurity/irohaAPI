package openFire.security.monitoring;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import iroha.protocol.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExampleSender {
    private static final Logger logger = Logger.getLogger(ExampleSender.class.getName());

    private final ManagedChannel channel;
    private final CommandServiceGrpc.CommandServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public ExampleSender(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    ExampleSender(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CommandServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " - verifier...");
        Commands.Command command =
                Commands.Command.newBuilder().setAddAssetQuantity(
                        Commands.AddAssetQuantity.newBuilder()
                                .setAccountId("verifier@test")
                                .setAmount(Primitive.Amount.newBuilder()
                                        .setValue(Primitive.uint256.newBuilder().setFirst(1).build())
                                        .setPrecision(0)
                                        .build())
                                .setAssetId("checks#test")
                                .build())
                        .build();
        BlockOuterClass.Transaction.Payload payload =
                BlockOuterClass.Transaction.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setCreatorAccountId("verifier@test")
                        .setTxCounter(1)
                        .addCommands(command)
                        .build();
        BlockOuterClass.Transaction request = BlockOuterClass.Transaction.newBuilder().setPayload(payload).build();
        Empty response;
        try {
            response = blockingStub.torii(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }

    public void greetFromSensor(String name) {
        logger.info("Will try to greet " + name + " - sensor...");
        Commands.Command command =
                Commands.Command.newBuilder().setSetAccountDetail(
                        Commands.SetAccountDetail.newBuilder()
                                .setAccountId("sensorid@test")
                                .setKey("1")
                                .setValue("2"))
                        .build();
        BlockOuterClass.Transaction.Payload payload =
                BlockOuterClass.Transaction.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setCreatorAccountId("sensorid@test")
                        .setTxCounter(1)
                        .addCommands(command)
                        .build();
        BlockOuterClass.Transaction request = BlockOuterClass.Transaction.newBuilder().setPayload(payload).build();
        Empty response;
        try {
            response = blockingStub.torii(request);
            logger.info(response.toString());
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }


    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        ExampleSender client = new ExampleSender("192.168.1.227", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
//            for (int i = 0; i< 10; i++) {
            client.greetFromSensor(user);
            client.greet(user);
//            }
        } finally {
            client.shutdown();
        }
    }
}