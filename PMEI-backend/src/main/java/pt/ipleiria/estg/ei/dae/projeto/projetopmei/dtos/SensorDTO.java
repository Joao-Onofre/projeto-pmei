package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    // Atributos
    private long sensorId;
    private String sensorType;
    private String statusType;
    private String formattedTimestamp; // Formato legível do timestamp
    private Double currentValue;

    // Construtores
    public SensorDTO() {}

    public SensorDTO(long sensorId, String sensorType, String statusType, String formattedTimestamp, Double currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.statusType = statusType;
        this.formattedTimestamp = formattedTimestamp;
        this.currentValue = currentValue;
    }

    // Métodos
    public static SensorDTO from(Sensor sensor) {
        // Define o formato desejado para o timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new SensorDTO(
                sensor.getSensorId(),
                sensor.getSensorType().getName(),
                sensor.getStatusType().getName(),
                sensor.getTimestamp() != null ? dateFormat.format(sensor.getTimestamp()) : null, // Formata o timestamp
                sensor.getCurrentValue()
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }

    // Getters and Setters
    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getFormattedTimestamp() {
        return formattedTimestamp;
    }

    public void setFormattedTimestamp(String formattedTimestamp) {
        this.formattedTimestamp = formattedTimestamp;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
}
