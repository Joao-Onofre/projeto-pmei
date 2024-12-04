package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.SensorTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;

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
    private StatusTypeBean statusTypeBean

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
            sensorBean.create(1L,  SensorType, statusType, LocalDateTime.now(), "25.0°C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
