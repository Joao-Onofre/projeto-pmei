package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SensorStatusType {

    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    @OneToMany(mappedBy = "status")
    private List<Sensor> sensors;

    //-------------- Construtores ----------------

    public SensorStatusType() {
    }
    public SensorStatusType(String status) {
        this.status = status;
        this.sensors = new ArrayList<Sensor>();
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------

    public long getId() {
        return id;
    }
    public void setId(long id) {
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
}