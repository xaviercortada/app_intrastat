package cat.alkaid.projects.intrastat.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties
public class Provincia {

	@Id
    private int codigo;
	private String name;
	
}
