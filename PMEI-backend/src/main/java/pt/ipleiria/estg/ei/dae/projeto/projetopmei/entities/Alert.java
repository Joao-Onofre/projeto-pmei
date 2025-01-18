package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    private Sensor sensor;

    public Alert() {}

    public Alert(String message, Date timestamp, Sensor sensor) {
        this.message = message;
        this.timestamp = timestamp;
        this.sensor = sensor;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
}
