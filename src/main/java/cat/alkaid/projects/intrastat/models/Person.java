package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Embeddable
public class Person {
	
    private String documento;
    private String firstName;
    private String lastName;
    private String email;
    
}
