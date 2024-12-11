package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sensorId;
    private String sensorType;
    private Long sensorTypeId;
    private String statusType;
    private Long statusTypeId;
    private Date timestamp;
    private Double currentValue;

    // Construtores
    public SensorDTO() {}

    public SensorDTO(long sensorId, String sensorType, String statusType) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.statusType = statusType;
    }

    // Metodos
    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getSensorId(),
                sensor.getSensorType().getName(),
                sensor.getStatusType().getName()
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
}
