package openFire.security.monitoring.examples;

import openFire.security.monitoring.Sender;

import java.util.logging.Logger;

public class SenderExample {
    private static final Logger logger = Logger.getLogger(SenderExample.class.getName());

    public static void main(String[] args) {
        Sender sender = null;
        try {
            sender = new Sender("192.168.1.227", 50051);

            sender.sendVerifierUpdate(true, "Everything is fine");
            sender.sendVerifierUpdate(false, "Sensor doesn't works");
            sender.sendVerifierUpdate(true, "Everything is fine");
            sender.sendVerifierUpdate(true, "Everything is fine");

            sender.sendSensorUpdate("Temperature", 30);
            sender.sendSensorUpdate("Temperature", 60);
            sender.sendSensorUpdate("Smoke", "no");
            sender.sendSensorUpdate("Smoke", "no");
            sender.sendSensorUpdate("Smoke", "yes");

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
