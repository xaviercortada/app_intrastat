package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Embeddable
public class Address {
	
    private String domicilio;
    private String poblacion;
    private String provincia;
    private String cpostal;
    private String pais;
    
    

}
