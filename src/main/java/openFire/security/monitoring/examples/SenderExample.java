package openFire.security.monitoring.examples;

import openFire.security.monitoring.Sender;

import java.util.logging.Logger;

public class SenderExample {
    private static final Logger logger = Logger.getLogger(SenderExample.class.getName());

    public static void main(String[] args) {
        Sender sender = null;
        try {
            sender = new Sender("192.168.1.227", 50051);

//            sender.sendVerifierUpdate("verifier@test", "sensorid@test", true, "Everything is fine");
//            sender.sendVerifierUpdate("verifier@test", "sensorid@test", false, "Sensor doesn't works");
//            sender.sendVerifierUpdate("verifier@test", "sensorid@test", true, "Everything is fine");
//            sender.sendVerifierUpdate("verifier@test", "sensorid@test", true, "Everything is fine");
//
//            sender.sendSensorUpdate("sensorid@test", "Temperature", 30);
//            sender.sendSensorUpdate("sensorid@test", "Temperature", 60);
            sender.sendSensorUpdate("sensorid@test", "Smoke", 120);
//            sender.sendSensorUpdate("sensorid@test", "Smoke", 10);
//            sender.sendSensorUpdate("sensorid@test", "Smoke", 0);

        } finally {
            try {
                if (sender != null) {
                    sender.shutdown();
                }
            } catch (InterruptedException e) {
                logger.warning("Can't send updates");
                e.printStackTrace();
            }
        }
    }

}
