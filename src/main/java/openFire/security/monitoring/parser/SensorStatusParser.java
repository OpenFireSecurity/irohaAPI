package openFire.security.monitoring.parser;

import com.alibaba.fastjson.JSON;
import openFire.security.monitoring.model.sensorResponse.SensorStatusMap;

import java.util.Map;

public class SensorStatusParser {
    public static SensorStatusMap parse(String json){
        SensorStatusMap sensorStatusMap = new SensorStatusMap((Map<String, Map<String, String>>)JSON.parse(json));
        return sensorStatusMap;
    }
}
