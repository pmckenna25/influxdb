package repository;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InfluxRespository {

    InfluxDB influxDB;
    Random fakeNumbers;
    BatchPoints batchPoints;

    public InfluxRespository() throws ParseException {

        influxDB = InfluxDBFactory.connect("http://localhost:8086/","root", "root");
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        fakeNumbers = new Random();
        batchPoints = BatchPoints
                .database("SensorData")
                .build();
        List<Long> times = createTimeObjects();


        for(Long time : times){

            populateInflux(time, "cks71rs", "cookstown", "temperature","red_sensor",
                    54.6418, 6.7444, "Celsius",(float) fakeNumbers.nextInt(5)+15);
        }
        for(Long time : times){

            populateInflux(time, "bfl72bs", "belfast", "humidity","blue_sensor",
                    54.5973, 5.9301,"%" ,(float) fakeNumbers.nextInt(10)+40);
        }

        for(Long time : times){

            populateInflux(time, "bfl73ws", "belfast", "light","white_sensor",
                    54.5973, 5.9301,"lumens" ,(float) fakeNumbers.nextInt(500)+5000);
        }

        for(Long time : times){

            populateInflux(time, "bfl71rs", "belfast", "temperature","red_sensor",
                    54.5973, 5.9301, "Celsius" ,(float) fakeNumbers.nextInt(7)+15);
        }

        for(Long time : times){

            populateInflux(time, "cksl72bs", "cookstown", "humidity","blue_sensor",
                    54.6418, 6.7444, "%",(float) fakeNumbers.nextInt(5)+30);
        }

        for(Long time : times){

            populateInflux(time, "cksl73ws", "cookstown", "light","white_sensor",
                    54.6418, 6.7444, "lumens",(float) fakeNumbers.nextInt(2000)+5000);
        }

        for(Long time : times){

            populateInflux(time, "nesl71rs", "newry", "temperature","red_sensor",
                    54.1751, 6.3402, "Celsius",(float) fakeNumbers.nextInt(5)+18);
        }

        for(Long time : times){

            populateInflux(time, "nesl72bs", "newry", "humidity","blue_sensor",
                    54.1751, 6.3402, "%",(float) fakeNumbers.nextInt(5)+40);
        }

        for(Long time : times){

            populateInflux(time, "nesl73ws", "newry", "light","white_sensor",
                    54.1751, 6.3402, "lumens",(float) fakeNumbers.nextInt(700)+5000);
        }

        for(Long time : times){

            populateInflux(time, "tbtl71rs", "timbuktu", "temperature","red_sensor",
                    16.7666, 3.0026, "Celsius",(float) fakeNumbers.nextInt(5)+30);
        }

        for(Long time : times){

            populateInflux(time, "tbtl72bs", "timbuktu", "humidity","blue_sensor",
                    16.7666, 3.0026, "%",(float) fakeNumbers.nextInt(5)+20);
        }

        for(Long time : times){

            populateInflux(time, "tbtl73ws", "timbuktu", "light","white_sensor",
                    16.7666, 3.0026, "lumens",(float) fakeNumbers.nextInt(700)+8000);
        }


        this.influxDB.write(this.batchPoints);
    }

    private void populateInflux(Long time, String sensorId, String location, String type,
                                String sensorType, double lon, double lat, String unit, float measuredValue){


        Point point = Point.measurement("sensors")
                .time(time, TimeUnit.MILLISECONDS)
                .tag("sensorid",sensorId)
                .tag("coarselocation",location)
                .tag("sensorclass",type)
                .addField("sensorlabel",sensorType)
                .addField("GPS", ("{Lon:"+lon+", Lat:"+lat+"}"))
                .addField("rawData",("{Unit:"+unit+", measuredValue:"+measuredValue+"}"))
                .build();
        this.batchPoints.point(point);
    }

    private List<Long> createTimeObjects() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d1 = formatter.parse("2019-02-28 09:00:00");
        Date d2 = formatter.parse("2019-02-28 10:00:00");
        Date d3 = formatter.parse("2019-02-28 11:00:00");
        Date d4 = formatter.parse("2019-02-28 12:00:00");
        Date d5 = formatter.parse("2019-02-28 13:00:00");
        Date d6 = formatter.parse("2019-02-28 14:00:00");
        Date d7 = formatter.parse("2019-02-28 15:00:00");
        Date d8 = formatter.parse("2019-02-28 16:00:00");
        Date d9 = formatter.parse("2019-02-28 17:00:00");
        Date d10 = formatter.parse("2019-02-28 18:00:00");
        Long t1 = d1.getTime();
        System.out.println(t1);
        Long t2 = d2.getTime();
        Long t3 = d3.getTime();
        Long t4 = d4.getTime();
        Long t5 = d5.getTime();
        Long t6 = d6.getTime();
        Long t7 = d7.getTime();
        Long t8 = d8.getTime();
        Long t9 = d9.getTime();
        Long t10 = d10.getTime();
        return new LinkedList<Long>(Arrays.asList(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10));
    }

    public InfluxDB getInfluxDB() {
        return influxDB;
    }

    public Random getFakeNumbers() {
        return fakeNumbers;
    }

    public static void main(String[] args) throws ParseException {

        InfluxRespository starter = new InfluxRespository();
    }
}
