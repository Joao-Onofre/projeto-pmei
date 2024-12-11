package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorStatusType;

import java.sql.Date;

@Entity
public class Sensor {
    //-------------- Atributos ----------------
    @Id
    private long id;
    @ManyToOne
    private SensorType sensorType;
    @ManyToOne
    private SensorStatusType status;
    private Date timestamp;
    private String currentValue;
    @ManyToOne
    private Package pack;

    //-------------- Construtores ----------------
    public Sensor() {
    }
    public Sensor(long id, SensorType sensorType, SensorStatusType status, Date timestamp, String currentValue) {
        this.id = id;
        this.sensorType = sensorType;
        this.status = status;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public SensorType getSensorType() {
        return sensorType;
    }
    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public SensorStatusType getStatus() {
        return status;
    }
    public void setStatus(SensorStatusType status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
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
