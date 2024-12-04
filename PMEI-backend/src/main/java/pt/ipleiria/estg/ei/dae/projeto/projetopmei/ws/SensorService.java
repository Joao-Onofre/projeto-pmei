package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.SensorDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.SensorBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
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

    // GET todos os sensores
    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors(){
        List<Sensor> sensors = sensorBean.findAll();
        return SensorDTO.from(sensors);
    }

    // POST novo sensor
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public
}
