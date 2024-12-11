package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SensorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String type;

    @OneToMany(mappedBy = "sensorType")
    private List<Sensor> sensors;

    // Constructor
    public SensorType() {
    }

    public SensorType(long id, String type) {
        this.id = id;
        this.type = type;
        this.sensors = new ArrayList<>();
    }

    // Getters / Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getName() {
        return this.type;
    }
}
