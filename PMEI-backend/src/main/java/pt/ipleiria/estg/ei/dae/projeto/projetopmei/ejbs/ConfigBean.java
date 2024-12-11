package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.Date;
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

            if (sensorTypeBean.findByName("Temperature") == null) {
                SensorType temperature = new SensorType();
                temperature.setType("Temperature");
                sensorTypeBean.create(temperature);  // Pass SensorType object
            }

            if (sensorTypeBean.findByName("Humidity") == null) {
                SensorType humidity = new SensorType();
                humidity.setType("Humidity");
                sensorTypeBean.create(humidity);  // Pass SensorType object
            }

            // Create predefined Status Types if they don't exist
            if (statusTypeBean.findByName("Active") == null) {
                StatusType active = new StatusType();
                active.setStatus("Active");
                statusTypeBean.create(active);  // Pass StatusType object
            }

            if (statusTypeBean.findByName("Inactive") == null) {
                StatusType inactive = new StatusType();
                inactive.setStatus("Inactive");
                statusTypeBean.create(inactive);  // Pass StatusType object
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
