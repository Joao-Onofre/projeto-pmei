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
    @JoinColumn(name = "sensorType_id", referencedColumnName = "id")
    private SensorType sensorType;

    @ManyToOne
    @JoinColumn(name = "statusType_id", referencedColumnName = "id")
    private StatusType statusType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private Double currentValue;

    @ManyToOne
    private Package pack;

    //-------------- Construtores ----------------
    public Sensor() {
        this.timestamp = new Date();  // Default timestamp to now
        this.currentValue = 0.0;      // Default current value to 0
        this.statusType = new StatusType("Inactive"); // Default statusType to "Inactive"
    }

    public Sensor(SensorType sensorType) {
        this();  // Default constructor to set timestamp and currentValue
        this.sensorType = sensorType;
    }

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

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = new Date();
        }
    }
}
