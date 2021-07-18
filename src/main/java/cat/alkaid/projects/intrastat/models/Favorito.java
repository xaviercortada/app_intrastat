package cat.alkaid.projects.intrastat.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favorito {

	@Id
	private String code;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Company company;

	
	
}
