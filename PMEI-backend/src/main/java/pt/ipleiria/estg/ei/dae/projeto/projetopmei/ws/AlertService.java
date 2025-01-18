package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.AlertDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.AlertBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.SensorBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.User;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("alert")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AlertService {

    @EJB
    private AlertBean alertBean;

    @EJB
    private SensorBean sensorBean;

    // GET: Retorna todos os alertas ou apenas os alertas do usuário (dependendo do papel)
    @GET
    @Path("/")
    public Response getAllAlerts(@QueryParam("username") String username, @QueryParam("role") String role) {
        try {
            if (role != null && role.equals("Administrator")) {
                // Se for Admin, retorna todos os alertas
                List<Alert> alerts = alertBean.findAll();
                return Response.ok(AlertDTO.from(alerts)).build();
            } else if (username != null) {
                // Se for usuário comum, retorna apenas os alertas desse usuário específico
                User user = alertBean.findUserByUsername(username);  // Método que busca o usuário
                List<Alert> alerts = alertBean.findByUser(user);
                return Response.ok(AlertDTO.from(alerts)).build();
            }
            return Response.status(Response.Status.FORBIDDEN).build();  // Se não fornecer role ou username, retorna 403
        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado: " + e.getMessage())
                    .build();
        }
    }

    // GET: Retorna um alerta pelo ID
    @GET
    @Path("/{id}")
    public Response getAlert(@PathParam("id") Long id, @QueryParam("username") String username, @QueryParam("role") String role) {
        try {
            Alert alert = alertBean.find(id);

            if (role != null && role.equals("Administrator")) {
                return Response.ok(AlertDTO.from(alert)).build();
            } else if (username != null && alert.getUser().getUsername().equals(username)) {
                return Response.ok(AlertDTO.from(alert)).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado. Este alerta não pertence ao usuário especificado.").build();
            }
        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Alerta não encontrado: " + e.getMessage())
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
            Sensor sensor = sensorBean.find(alertDTO.getSensorId());
            newAlert.setSensor(sensor);

            // Associar o usuário ao alerta (tratando a exceção se o usuário não for encontrado)
            User user = alertBean.findUserByUsername(alertDTO.getUsername());  // Novo método no AlertBean
            newAlert.setUser(user);

            newAlert.setMessage(alertDTO.getMessage());

            alertBean.create(newAlert, user);

            return Response.status(Response.Status.CREATED)
                    .entity(AlertDTO.from(newAlert))
                    .build();

        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor ou usuário não encontrado: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar alerta: " + e.getMessage())
                    .build();
        }
    }
}
