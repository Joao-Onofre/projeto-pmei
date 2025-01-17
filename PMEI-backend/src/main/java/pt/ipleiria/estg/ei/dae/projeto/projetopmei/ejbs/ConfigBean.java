package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.ProductDTO;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.SensorTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.StatusTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.ProductTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.List;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private PackageTypeBean packageTypeBean;
    @EJB
    private ProductTypeBean productTypeBean;

    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private OrderStatusBean orderStatusBean;
    @EJB
    private OrderBean orderBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorTypeBean sensorTypeBean;

    @EJB
    private StatusTypeBean statusTypeBean;

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {

        //Tipos de embalagens
        packageTypeBean.create("Food");
        packageTypeBean.create("Isometric");
        packageTypeBean.create("Fragile");
        packageTypeBean.create("Appliances");
        packageTypeBean.create("Default");

        //Tipos de status das encomendas
        orderStatusBean.create("Processing");
        orderStatusBean.create("Processed");
        orderStatusBean.create("In Transit");
        orderStatusBean.create("Stuck in Transit");
        orderStatusBean.create("Delivered");
        orderStatusBean.create("Canceled");

        //Tipos de produtos
        productTypeBean.create("Fresh Food");
        productTypeBean.create("Frozen Food");
        productTypeBean.create("Appliances");
        productTypeBean.create("Canned Food");
        productTypeBean.create("Clothes");

        //Produtos
        productBean.create("TV", "Televisão", 150.0f, 3);
        productBean.create("Gelado Morango", "Gelado de Morango", 1.50f, 2);
        productBean.create("Salsichas Enlatadas", "Salsichas enlatadas 8 unidades", 2.50f, 4);

        try {
            // Creating Administrator if not already exists
            administratorBean.create("admin", "123", "Administrator", "admin@mail.pt");

            // Ensure "Temperature" SensorType exists
            if (sensorTypeBean.findByName("Temperature") == null) {
                SensorType temperature = new SensorType();
                temperature.setType("Temperature");
                sensorTypeBean.create(temperature);
            }

            // Ensure "Humidity" SensorType exists
            if (sensorTypeBean.findByName("Humidity") == null) {
                SensorType humidity = new SensorType();
                humidity.setType("Humidity");
                sensorTypeBean.create(humidity);
            }

            // Ensure predefined Status Types exist
            if (statusTypeBean.findByName("Active") == null) {
                StatusType active = new StatusType();
                active.setStatus("Active");
                statusTypeBean.create(active);
            }

            if (statusTypeBean.findByName("Inactive") == null) {
                StatusType inactive = new StatusType();
                inactive.setStatus("Inactive");
                statusTypeBean.create(inactive);
            }

            // Get the required types
            SensorType temperatureSensorType = sensorTypeBean.findByName("Temperature");
            StatusType inactiveStatus = statusTypeBean.findByName("Inactive");

            if (temperatureSensorType != null && inactiveStatus != null) {
                // Create 10 temperature sensors
                for (int i = 0; i < 10; i++) {
                    Sensor tempSensor = new Sensor(temperatureSensorType);
                    tempSensor.setStatusType(inactiveStatus);
                    tempSensor.setCurrentValue(0.0); // Set initial value to 0
                    sensorBean.create(tempSensor);
                }
            }

            // Create a humidity sensor
            SensorType humiditySensorType = sensorTypeBean.findByName("Humidity");
            if (humiditySensorType != null) {
                Sensor humiditySensor = new Sensor(humiditySensorType);
                humiditySensor.setStatusType(inactiveStatus);
                humiditySensor.setCurrentValue(0.0);
                sensorBean.create(humiditySensor);
            }

            customerBean.create("customer1", "1234", "Joner Boner", "boner@gmail.com");
            productBean.create("jonkler", "skibidi", 69, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}