package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.OrderStatusBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.PackageTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.ProductTypeBean;

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
    private ProductBean productBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private OrderStatusBean orderStatusBean;
    @EJB
    private OrderBean orderBean;

    @PostConstruct
    public void populateDB() {

        //Tipos de embalagens
        packageTypeBean.create("Food");
        packageTypeBean.create("Fresh/Frozen Food");
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

        //Orders
        orderBean.create(1,"1", "Default");

        productTypeBean.create("jonk");

        try {
            administratorBean.create("admin", "123", "Administrator", "admin@mail.pt");
            productBean.create("jonkler", "skibidi", 69, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
