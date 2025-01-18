package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alert {
    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long alertId;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "sensorId")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user; // Adiciona a relação com o User

    private LocalDateTime timestamp;

    private String message;

    //-------------- Construtores ----------------
    public Alert() {
        this.timestamp = LocalDateTime.now(); // Default timestamp to now
    }

    public Alert(Sensor sensor, User user, String message) {
        this();
        this.sensor = sensor;
        this.user = user;
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

    public User getUser() {
        return user; // Adiciona o getter para o usuário
    }

    public void setUser(User user) {
        this.user = user; // Adiciona o setter para o usuário
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
