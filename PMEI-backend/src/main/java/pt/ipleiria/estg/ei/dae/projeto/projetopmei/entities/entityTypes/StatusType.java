package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

@Entity
public class StatusType {

    //-------------- Atributos ----------------
    @Id
    private long id;
    private String status;
    @OneToMany(mappedBy = "status")
    private List<Sensor> sensors;

    //-------------- Construtores ----------------

    public StatusType() {
    }
    public StatusType(long id, String status) {
        this.id = id;
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
