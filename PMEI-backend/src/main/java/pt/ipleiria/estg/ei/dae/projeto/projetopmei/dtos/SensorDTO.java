package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    // Atributos
    private long sensorId;
    private String sensorType;
    private Long sensorTypeId;
    private String statusType;
    private Long statusTypeId;
    private Date timestamp;
    private Double currentValue;

    // Construtores
    public SensorDTO() {}

    public SensorDTO(long sensorId, String sensorType, Long sensorTypeId, String statusType, Long statusTypeId, Date timestamp, Double currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.sensorTypeId = sensorTypeId;
        this.statusType = statusType;
        this.statusTypeId = statusTypeId;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
    }

    // Metodos
    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getSensorId(),
                sensor.getSensorType().getName(),
                sensor.getSensorType().getId(),
                sensor.getStatusType().getName(),
                sensor.getStatusType().getId(),
                sensor.getTimestamp(),
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

    public Long getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(Long sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Long getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(Long statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
}
