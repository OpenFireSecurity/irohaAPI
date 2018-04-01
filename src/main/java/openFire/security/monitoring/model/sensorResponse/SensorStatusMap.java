package openFire.security.monitoring.model.sensorResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SensorStatusMap {
    Map<String, SensorParametersMap> statusMap;

    public SensorStatusMap(Map<String, Map<String, String>> statusMap) {
        this.statusMap = new HashMap<>();
        for (String key : statusMap.keySet()) {
            this.statusMap.put(key, new SensorParametersMap(statusMap.get(key)));
        }
    }

    public SensorStatusMap() {
        statusMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return statusMap.toString();
    }

    public Set<String> getSensorIds() {
        return statusMap.keySet();
    }

    public SensorParametersMap getParametersBySensorId(String sensorId) {
        return statusMap.get(sensorId);
    }

}
