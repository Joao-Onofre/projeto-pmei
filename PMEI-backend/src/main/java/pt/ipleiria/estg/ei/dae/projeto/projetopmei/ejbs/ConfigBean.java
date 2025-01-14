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

        // Creating predefined Package Types
        packageTypeBean.create(1, "Eletro-doméstivos");
        packageTypeBean.create(2, "Frutas");
        packageTypeBean.create(3, "Legumes");
        packageTypeBean.create(4, "Carne Fresca");
        packageTypeBean.create(5, "Peixe Fresco");
        packageTypeBean.create(6, "Congelados");

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

            // Create sensors with default values for SensorType and StatusType
            SensorType sensorType = sensorTypeBean.findByName("Temperature");
            StatusType statusType = statusTypeBean.findByName("Inactive");

            if (sensorType != null && statusType != null) {
                // Create a Sensor object with the SensorType and StatusType
                Sensor newSensor = new Sensor(sensorType);  // You may pass any additional fields you need (timestamp, currentValue, etc.)
                newSensor.setStatusType(statusType);  // Set the status for the Sensor

                // Save the sensor
                sensorBean.create(newSensor); // This now uses the updated create method in SensorBean
            }

            // Create more sensors if needed with different types or statuses
            SensorType humiditySensorType = sensorTypeBean.findByName("Humidity");
            if (humiditySensorType != null) {
                Sensor newHumiditySensor = new Sensor(humiditySensorType); // Create a new sensor for humidity
                newHumiditySensor.setStatusType(statusType); // Set the status for the new sensor

                // Save the new humidity sensor
                sensorBean.create(newHumiditySensor); // This will persist the new sensor
            }

            customerBean.create("customer1", "1234", "Joner Boner", "boner@gmail.com");
            productBean.create("jonkler", "skibidi", 69, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
