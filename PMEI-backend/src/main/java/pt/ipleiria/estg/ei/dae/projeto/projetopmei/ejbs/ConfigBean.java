package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private AdministratorBean administratorBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        try {
            administratorBean.create("admin", "123", "Administrator", "admin@mail.pt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
