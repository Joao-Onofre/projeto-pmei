package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SensorStatusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String status;

    @OneToMany(mappedBy = "statusType")
    private List<Sensor> sensors;

    // Constructor
    public StatusType() {
    }

    public StatusType(Long id, String status) {
        this.id = id;
        this.status = status;
        this.sensors = new ArrayList<>();
    }

    // Constructor with just status
    public StatusType(String status) {
        this.status = status;
        this.sensors = new ArrayList<>();
    }

    // Getters / Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getName() {
        return this.status;
    }
}
