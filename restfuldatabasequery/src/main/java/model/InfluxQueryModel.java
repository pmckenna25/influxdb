package model;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InfluxQueryModel {

    static InfluxDB influxDB;
    List<SensorData> data;

    public InfluxQueryModel(){

        influxDB = InfluxDBFactory.connect("http://localhost:8086/","root", "root");
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        data = new LinkedList<>();
    }

    public List<SensorData> sensorsByLocation(String location){

        Query query = new Query("Select * from sensors WHERE coarselocation=\'"+location+"\'", "SensorData");
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<SensorData> results = resultMapper.toPOJO(queryResult, SensorData.class);
        return results;
    }

    public List<SensorData> sensorsByType(String type){

        Query query = new Query("Select * from sensors WHERE sensorclass=\'"+type+"\'", "SensorData");
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<SensorData> results = resultMapper.toPOJO(queryResult, SensorData.class);
        return results;
    }

    public List<SensorData> sensorsByLocationAndType(String location, String type){
        Set<SensorData> dt1 = new HashSet<>(sensorsByLocation(location));
        Set<SensorData> dt2 = new HashSet<>(sensorsByType(type));
        dt1.retainAll(dt2);
        return new LinkedList<>(dt1);
    }

    public SensorData maxValueSensor(String type){

        double max = Double.MIN_VALUE;
        SensorData result = null;
        List<SensorData> sensorData = sensorsByType(type);
        for(SensorData data : sensorData){

            String[] splitData = data.getMeasuredValues().split(",");
            String value = splitData[splitData.length-1];
            String trimmed =  value.substring(value.indexOf(":")+1, value.indexOf("}"));
            double possibleMax = Double.parseDouble(trimmed);
            if(possibleMax > max){

                max = possibleMax;
                result = data;
            }
        }
        return result;
    }

    public SensorData minValueSensor(String type){

        double min = Double.MAX_VALUE;
        SensorData result = null;
        List<SensorData> sensorData = sensorsByType(type);
        for(SensorData data : sensorData){

            String[] splitData = data.getMeasuredValues().split(",");
            String value = splitData[splitData.length-1];
            String trimmed =  value.substring(value.indexOf(":")+1, value.indexOf("}"));
            double possibleMin = Double.parseDouble(trimmed);
            if(possibleMin < min){

                min = possibleMin;
                result = data;
            }
        }
        return result;
    }
}
