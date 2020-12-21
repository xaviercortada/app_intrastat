package cat.alkaid.projects.intrastat.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Periodo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int month;
    private int year;
    
}
