package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {
    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");
    }
}
