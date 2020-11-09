package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nomenclature {

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
	public Object getCodeCN8() {
		return null;
	}
	
}
