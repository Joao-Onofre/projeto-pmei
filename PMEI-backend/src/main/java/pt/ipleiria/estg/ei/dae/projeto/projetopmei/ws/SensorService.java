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
        SensorType sensorType = sensorTypeBean.findByName(sensorDTO.getSensorType());
        StatusType statusType = statusTypeBean.findByName(sensorDTO.getStatusType());

        if (sensorType == null || statusType == null) {
            throw new MyEntityNotFoundException("Invalid SensorType or StatusType");
        }

        sensorBean.create(
                sensorDTO.getSensorId(),
                sensorType,
                statusType,
                sensorDTO.getTimestamp(),
                sensorDTO.getCurrentValue()
        );

        Sensor newSensor = sensorBean.find(sensorDTO.getSensorId());
        return Response.status(Response.Status.CREATED)
                .entity(SensorDTO.from(newSensor))
                .build();
    }

    // Sensor por ID
    @GET
    @Path("{id}")
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

        sensorBean.update(
                sensorDTO.getSensorId(),
                sensorType,
                statusType,
                sensorDTO.getTimestamp(),
                sensorDTO.getCurrentValue()
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
