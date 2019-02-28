package service;

import model.InfluxQueryModel;
import model.SensorData;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("data")
public class RestServlet {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage(){

        return "server alive\n";
    }

    @GET
    @Path("/location/{place}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorData> querySensorsAtLocation(@PathParam("place") String location){

        location = location.toLowerCase();
        InfluxQueryModel query = new InfluxQueryModel();
        List<SensorData> data = query
                .sensorsByLocation(location);
        return data;
    }

    @GET
    @Path("/type/{datatype}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorData> querySensorType(@PathParam("datatype") String dataType){

        dataType = dataType.toLowerCase();
        InfluxQueryModel query = new InfluxQueryModel();
        List<SensorData> data = query
                .sensorsByType(dataType);
        return data;
    }

    @GET
    @Path("/type/{datatype}/location/{location}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorData> querySensorTypeAndLocation(@PathParam("datatype") String dataType,
                                                       @PathParam("location") String location){

        dataType = dataType.toLowerCase();
        location = location.toLowerCase();
        InfluxQueryModel query = new InfluxQueryModel();
        List<SensorData> data = query
                .sensorsByLocationAndType(location, dataType);
        return data;
    }

    @GET
    @Path("/max/{datatype}")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorData getMaxValueOfSensorType(@PathParam("datatype") String dataType){
        dataType = dataType.toLowerCase();
        InfluxQueryModel query = new InfluxQueryModel();

        return query.maxValueSensor(dataType);
    }

    @GET
    @Path("/min/{datatype}")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorData getMinValueOfSensorType(@PathParam("datatype") String dataType){
        dataType = dataType.toLowerCase();
        InfluxQueryModel query = new InfluxQueryModel();

        return query.minValueSensor(dataType);
    }
}
