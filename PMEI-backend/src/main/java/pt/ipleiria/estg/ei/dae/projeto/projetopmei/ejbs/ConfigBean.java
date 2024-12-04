package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.SensorTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.time.LocalDateTime;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private PackageTypeBean packageTypeBean;

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorTypeBean sensorTypeBean;
    @EJB
    private StatusTypeBean statusTypeBean;

    @PostConstruct
    public void populateDB() {

        packageTypeBean.create(1, "Eletro-doméstivos");
        packageTypeBean.create(2, "Frutas");
        packageTypeBean.create(3, "Legumes");
        packageTypeBean.create(4, "Carne Fresca");
        packageTypeBean.create(5, "Peixe Fresco");
        packageTypeBean.create(6, "Congelados");

        try {
            administratorBean.create("admin", "123", "Administrator", "admin@mail.pt");

            // Vai buscar à BD
            SensorType sensorType = sensorTypeBean.findById(1L); // Fetching SensorType by ID
            StatusType statusType = statusTypeBean.findById(1L); // Fetching StatusType by ID

            if (sensorType != null && statusType != null) {
                // Cria sensor
                sensorBean.create(1L, sensorType, statusType, LocalDateTime.now(), 25.0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
