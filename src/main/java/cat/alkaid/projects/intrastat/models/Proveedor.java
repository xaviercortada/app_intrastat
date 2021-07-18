package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Proveedor {
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
    private String codigo;
    private String name;
    
    @Embedded
    private Address address;
    
    @Embedded
    private Person person;

}
