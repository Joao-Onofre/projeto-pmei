package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.AlertDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.AlertBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.SensorBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Authenticated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("alert")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
//@RolesAllowed({"Administrator"})
public class AlertService {

    @EJB
    private AlertBean alertBean;

    @EJB
    private SensorBean sensorBean;

    // GET: Retorna todos os alertas
    @GET
    @Path("/")
    public List<AlertDTO> getAllAlerts() {
        List<Alert> alerts = alertBean.findAll();
        return AlertDTO.from(alerts);
    }

    // GET: Retorna um alerta pelo ID
    @GET
    @Path("/{id}")
    public Response getAlert(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Alert alert = alertBean.find(id);
        return Response.ok(AlertDTO.from(alert)).build();
    }

    // GET: Retorna todos os alertas de um sensor específico
    @GET
    @Path("/sensor/{sensorId}")
    public List<AlertDTO> getAlertsBySensor(@PathParam("sensorId") Long sensorId) {
        List<Alert> alerts = alertBean.findBySensor(sensorId);
        return AlertDTO.from(alerts);
    }

    // GET: Retorna todos os alertas associados a uma lista de sensores
    @GET
    @Path("/sensors")
    public Response getAlertsBySensorIds(@QueryParam("ids") List<Long> sensorIds) {
        try {
            List<Alert> alerts = alertBean.findBySensorIds(sensorIds);
            return Response.ok(AlertDTO.from(alerts)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar alertas: " + e.getMessage())
                    .build();
        }
    }


    // POST: Cria um alerta manualmente
    @POST
    @Path("/")
    public Response createAlert(AlertDTO alertDTO) {
        try {
            Alert newAlert = new Alert();

            // Converter o formattedTimestamp para LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime timestamp = LocalDateTime.parse(alertDTO.getFormattedTimestamp(), formatter);
            newAlert.setTimestamp(timestamp);

            // Buscar o Sensor pelo sensorId
            Sensor sensor = sensorBean.find(alertDTO.getSensorId()); // Aqui busca o Sensor pelo ID
            newAlert.setSensor(sensor);

            newAlert.setMessage(alertDTO.getMessage());

            alertBean.create(newAlert);

            return Response.status(Response.Status.CREATED)
                    .entity(AlertDTO.from(newAlert))
                    .build();

        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor não encontrado com o ID especificado: " + alertDTO.getSensorId())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar alerta: " + e.getMessage())
                    .build();
        }
    }

    // DELETE: Remove um alerta pelo ID
    @DELETE
    @Path("/{id}")
    public Response deleteAlert(@PathParam("id") Long id) throws MyEntityNotFoundException {
        alertBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }
}
