package openFire.security.monitoring.sensorResponseModel;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SensorParametersMap {
    private static final Logger logger = Logger.getLogger(SensorParametersMap.class.getName());

    Map<SensorParameter, String> infoFromSensor;

    public SensorParametersMap(Map<String, String> infoFromSensor) {
        this.infoFromSensor = new HashMap<>();
        for (String key : infoFromSensor.keySet()) {
            try {
                this.infoFromSensor.put(SensorParameter.valueOf(key), infoFromSensor.get(key));
            } catch (Exception e) {
                logger.warning("Unknown session parameter");
            }
        }
    }

    public SensorParametersMap() {
        infoFromSensor = new HashMap<>();
    }

    @Override
    public String toString() {
        return "SensorParametersMap" + infoFromSensor;
    }

    public Map<SensorParameter, String> getInfoFromSensor() {
        return infoFromSensor;
    }

    public void setInfoFromSensor(Map<SensorParameter, String> infoFromSensor) {
        this.infoFromSensor = infoFromSensor;
    }

    public String getParameterValue(SensorParameter sensorParameter) {
        return infoFromSensor.get(sensorParameter);
    }
}
