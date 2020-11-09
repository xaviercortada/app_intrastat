package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transporte {
	
	@Id
	private String codigo;
    private String name;

}
