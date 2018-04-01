package openFire.security.monitoring.examples;

import openFire.security.monitoring.Receiver;
import openFire.security.monitoring.sensorResponseModel.SensorStatusMap;

import java.util.List;
import java.util.logging.Logger;

public class ReceiverExample {
    private static final Logger logger = Logger.getLogger(ReceiverExample.class.getName());

    public static void main(String[] args) {
        Receiver receiver = null;
        try {
            receiver = new Receiver("192.168.1.227", 50051);

            List<iroha.protocol.BlockOuterClass.Transaction> allVerifierUpdates = receiver.getAllVerifierUpdates("verifier@test");

            SensorStatusMap uniqueSensorUpdates = receiver.getUniqueSensorUpdates("sensorid@test");
            List<iroha.protocol.BlockOuterClass.Transaction> allSensorUpdates = receiver.getAllSensorUpdates();



            logger.info("allVerifierUpdates: " + allVerifierUpdates.toString());
            logger.info("uniqueSensorUpdates: " + uniqueSensorUpdates.toString());
            logger.info("allSensorUpdates: " + allSensorUpdates.toString());

        } finally {
            try {
                if (receiver != null) {
                    receiver.shutdown();
                }
            } catch (InterruptedException e) {
                logger.warning("Can't send updates");
                e.printStackTrace();
            }
        }
    }

}
