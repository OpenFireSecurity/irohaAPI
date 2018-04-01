package openFire.security.monitoring.model;

import iroha.protocol.BlockOuterClass;
import openFire.security.monitoring.model.SensorParameter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SensorTransaction {
    private LocalDateTime localDateTime;
    private String sensorId;
    private SensorParameter parameter;
    private Double value;

    @Override
    public String toString() {
        return "localDateTime=" + localDateTime +
                ", sensorId='" + sensorId + '\'' +
                ", parameter=" + parameter +
                ", value=" + value + "\n";
    }

    public SensorTransaction(BlockOuterClass.Transaction transaction) {
        this.localDateTime = Instant.ofEpochMilli(transaction.getPayload().getCreatedTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.sensorId = transaction.getPayload().getCommands(0).getSetAccountDetail().getAccountId();
        this.parameter = SensorParameter.valueOf(transaction.getPayload().getCommands(0).getSetAccountDetail().getKey());
        this.value = Double.parseDouble(transaction.getPayload().getCommands(0).getSetAccountDetail().getValue());
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getSensorId() {
        return sensorId;
    }

    public SensorParameter getParameter() {
        return parameter;
    }

    public Double getValue() {
        return value;
    }
}
