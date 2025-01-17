package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.typesDTOs;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageTypeDTO implements Serializable {
    private long id;
    private String type;

    public PackageTypeDTO(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public static List<PackageTypeDTO> from(List<PackageType> types) {
        return types.stream()
                .map(type -> new PackageTypeDTO(type.getId(), type.getType()))
                .collect(Collectors.toList());
    }

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
}
