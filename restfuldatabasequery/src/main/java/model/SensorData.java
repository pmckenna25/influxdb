package model;


import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;
import java.util.Objects;

@Measurement(name = "sensors")
public class SensorData {

    @Column(name = "time")
    private Instant time;

    @Column(name = "sensorid")
    private String sensorid;

    @Column(name = "coarselocation")
    private String location;

    @Column(name = "GPS")
    private String gps;

    @Column(name = "sensorclass")
    private String type;

    @Column(name = "sensorlabel")
    private String label;

    @Column(name = "rawData")
    private String measuredValues;

    public SensorData(){

    }

    public Instant getTime() {
        return time;
    }

    public String getSensorid() {
        return sensorid;
    }

    public String getLocation() {
        return location;
    }

    public String getLabel() {
        return label;
    }

    public String getGps() {
        return gps;
    }

    public String getType() {
        return type;
    }

    public String getMeasuredValues() {
        return measuredValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorData that = (SensorData) o;
        return time.equals(that.time) &&
                sensorid.equals(that.sensorid) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, sensorid, location);
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "time=" + time +
                ", sensorid='" + sensorid + '\'' +
                ", location='" + location + '\'' +
                ", gps='" + gps + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", measuredValues=" + measuredValues +
                '}';
    }
}