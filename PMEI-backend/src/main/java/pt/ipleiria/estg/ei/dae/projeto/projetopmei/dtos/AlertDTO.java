package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AlertDTO implements Serializable {
    // Atributos
    private long alertId;
    private String message;
    private String formattedTimestamp; // Formato legível do timestamp
    private Long sensorId; // ID do sensor associado

    // Construtores
    public AlertDTO() {}

    public AlertDTO(long alertId, String message, String formattedTimestamp, Long sensorId) {
        this.alertId = alertId;
        this.message = message;
        this.formattedTimestamp = formattedTimestamp;
        this.sensorId = sensorId;
    }

    // Métodos de conversão
    public static AlertDTO from(Alert alert) {
        // Define o formato desejado para o timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new AlertDTO(
                alert.getAlertId(),
                alert.getMessage(),
                alert.getTimestamp() != null ? dateFormat.format(alert.getTimestamp()) : null, // Formata o timestamp
                alert.getSensor() != null ? alert.getSensor().getSensorId() : null // Pega o ID do sensor associado
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
}
