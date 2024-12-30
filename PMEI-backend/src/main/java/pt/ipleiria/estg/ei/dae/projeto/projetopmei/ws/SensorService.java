package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.SensorDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.SensorBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.SensorTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;

import java.util.List;

@Path("sensor")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Administrator"})
public class SensorService {

    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorTypeBean sensorTypeBean;

    @EJB
    private StatusTypeBean statusTypeBean;

    // GET todos os sensores
    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() throws Exception {
        List<Sensor> sensors = sensorBean.findAll();
        return SensorDTO.from(sensors);
    }

    // POST novo sensor
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewSensor(SensorDTO sensorDTO) throws MyEntityNotFoundException, Exception {
        // Find the SensorType based on the name provided in the DTO
        SensorType sensorType = sensorTypeBean.findByName(sensorDTO.getSensorType());

        if (sensorType == null) {
            throw new MyEntityNotFoundException("Invalid SensorType");
        }

        // Create a new Sensor using the custom constructor that sets default values
        Sensor newSensor = new Sensor(sensorType);  // Only provide the sensorType

        // Save the new Sensor
        sensorBean.create(newSensor);

        // Return the created Sensor as a response
        return Response.status(Response.Status.CREATED)
                .entity(SensorDTO.from(newSensor))
                .build();
    }


    // Sensor por ID
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") Long id) throws MyEntityNotFoundException, Exception {
        Sensor sensor = sensorBean.find(id);
        return Response.ok(SensorDTO.from(sensor))
                .build();
    }

    // Update
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSensor(SensorDTO sensorDTO) throws MyEntityNotFoundException, Exception {
        SensorType sensorType = sensorTypeBean.findByName(sensorDTO.getSensorType());
        StatusType statusType = statusTypeBean.findByName(sensorDTO.getStatusType());

        if (sensorType == null || statusType == null) {
            throw new MyEntityNotFoundException("Invalid SensorType or StatusType");
        }

        // Assuming sensorDTO no longer has timestamp and currentValue
        // Fetch the current sensor to retain timestamp and currentValue
        Sensor existingSensor = sensorBean.find(sensorDTO.getSensorId());
        if (existingSensor == null) {
            throw new MyEntityNotFoundException("Sensor not found with ID: " + sensorDTO.getSensorId());
        }

        // Update the sensor with the existing timestamp and current value
        sensorBean.update(
                sensorDTO.getSensorId(),
                sensorType,
                statusType,
                existingSensor.getTimestamp(),
                existingSensor.getCurrentValue()
        );

        return Response.status(Response.Status.OK).build();
    }


    // Delete
    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") Long id) throws MyEntityNotFoundException, Exception {
        sensorBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
