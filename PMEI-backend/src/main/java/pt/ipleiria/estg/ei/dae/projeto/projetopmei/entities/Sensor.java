package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sensor {
    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sensorId;
    @ManyToOne
    private SensorType sensorType;
    @ManyToOne
    private StatusType status;
    private LocalDateTime timestamp;
    private String currentValue;
    @ManyToOne
    private Package pack;

    //-------------- Construtores ----------------
    public Sensor() {
    }
    public Sensor(long sensorId, SensorType sensorType, StatusType status, LocalDateTime timestamp, String currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.status = status;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public long getSensorId() {
        return sensorId;
    }
    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }
    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public StatusType getStatus() {
        return status;
    }
    public void setStatus(StatusType status) {
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

    public Package getPack() {
        return pack;
    }
    public void setPack(Package pack) {
        this.pack = pack;
    }
}
