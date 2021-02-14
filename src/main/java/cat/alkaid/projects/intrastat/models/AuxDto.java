

package cat.alkaid.projects.intrastat.models;

public class AuxDto {
    private String id;
    private String codigo;
    private String description;

    public AuxDto(int id, String codigo, String description) {
        this.id = Integer.toString(id);
        this.codigo = codigo;
        this.description = description;
    }

    public AuxDto(String id, String codigo, String description) {
        this.id = id;
        this.codigo = codigo;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescription() {
    
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
