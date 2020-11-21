package cat.alkaid.projects.intrastat.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favorito {

	private Integer level;
	@Id
	private String code;
	private String section;
	private String description;
	
	private String sunit;
	private String sunitDesc;

	private String englishDesc;
	private String frenchDesc;
	private String germanDesc;
	
	
}
