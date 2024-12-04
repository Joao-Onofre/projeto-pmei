package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Administrator;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    // Atributos
    private long sensorId;
    private String sensorType;
    private String statusType;
    private LocalDateTime timestamp;
    private Double currentValue;

    // Construtores
    public SensorDTO() {}

    public SensorDTO(long sensorId, String sensorType, String statusType, LocalDateTime timestamp, Double currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.statusType = statusType;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
    }

    // Metodos
    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getSensorId(),
                sensor.getSensorType().getName(),
                sensor.getStatusType().getName(),
                sensor.getTimestamp(),
                sensor.getCurrentValue()
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }

    // Getters e Setters

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
}
