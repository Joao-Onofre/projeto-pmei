package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SensorType {

    //------------ Atributos -----------------
    @Id
    private long id;
    private String type;

    @OneToMany(mappedBy = "sensorType")
    private List<Sensor> sensors;

    //--------------- Construtores -----------------
    public SensorType() {
    }
    public SensorType(long id, String type, List<Sensor> sensors) {
        this.id = id;
        this.type = type;
        this.sensors = new ArrayList<Sensor>();
    }

    //--------------- Metodos ----------------

    //--------------- Getters / Setters -----------------

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
}
