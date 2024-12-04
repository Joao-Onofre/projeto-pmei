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
    private String status;
    private LocalDateTime timestamp;
    private String currentValue;

    // Construtores
    public SensorDTO() {}

    public SensorDTO(long sensorId, String sensorType, String status, LocalDateTime timestamp, String currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.status = status;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
    }

    // Metodos
    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getSensorId(),
                sensor.getSensorType().getName(),
                sensor.getStatus().getName(),
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
