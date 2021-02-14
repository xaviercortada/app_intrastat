package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Person {
	
    private String documento;
    private String firstName;
    private String lastName;
    private String email;

    public String getFullName() {
        return firstName + ' ' + lastName;
    } 
    
}
