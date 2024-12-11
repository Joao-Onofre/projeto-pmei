package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.util.Date;
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
    private StatusType statusType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private Double currentValue;
    @ManyToOne
    private Package pack;

    //-------------- Construtores ----------------
    public Sensor() {
    }
    public Sensor(long sensorId, SensorType sensorType, StatusType statusType, Date timestamp, Double currentValue) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.statusType = statusType;
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

    public StatusType getStatusType() {
        return statusType;
    }
    public void setStatusType(StatusType status) {
        this.statusType = status;
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

    public Package getPack() {
        return pack;
    }
    public void setPack(Package pack) {
        this.pack = pack;
    }
}
