package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Alert {
    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long alertId;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "sensorId")
    private Sensor sensor;

    private LocalDateTime timestamp;

    private String message;

    //-------------- Construtores ----------------
    public Alert() {
        this.timestamp = LocalDateTime.now(); // Default timestamp to now
    }

    public Alert(Sensor sensor, String message) {
        this();
        this.sensor = sensor;
        this.message = message;
    }

    //-------------- Getters / Setters ----------------
    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //-------------- Métodos Auxiliares ----------------
    public Double getSensorCurrentValue() {
        if (sensor != null) {
            return sensor.getCurrentValue();
        }
        return null; // Retorna null se o sensor não estiver associado
    }
}
