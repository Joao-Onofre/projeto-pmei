package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.User;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AlertDTO implements Serializable {
    // Atributos
    private long alertId;
    private String message;
    private String formattedTimestamp; // Formato legível do timestamp
    private Long sensorId; // ID do sensor associado
    private String username; // Nome de usuário associado ao alerta

    // Construtores
    public AlertDTO() {}

    public AlertDTO(long alertId, String message, String formattedTimestamp, Long sensorId, String username) {
        this.alertId = alertId;
        this.message = message;
        this.formattedTimestamp = formattedTimestamp;
        this.sensorId = sensorId;
        this.username = username;
    }

    // Métodos de conversão
    public static AlertDTO from(Alert alert) {
        // Define o formato desejado para o timestamp usando DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Cria o DTO a partir do alerta
        return new AlertDTO(
                alert.getAlertId(),
                alert.getMessage(),
                alert.getTimestamp() != null ? alert.getTimestamp().format(formatter) : null, // Formata o LocalDateTime
                alert.getSensor() != null ? alert.getSensor().getSensorId() : null, // Pega o ID do sensor associado
                alert.getUser() != null ? alert.getUser().getUsername() : null // Pega o username do usuário associado
        );
    }

    public static List<AlertDTO> from(List<Alert> alerts) {
        return alerts.stream().map(AlertDTO::from).collect(Collectors.toList());
    }

    // Getters and Setters
    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormattedTimestamp() {
        return formattedTimestamp;
    }

    public void setFormattedTimestamp(String formattedTimestamp) {
        this.formattedTimestamp = formattedTimestamp;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
