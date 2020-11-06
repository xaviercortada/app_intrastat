package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favorito implements Serializable {

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
	
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCode() {
		return code;
	}
	
	@Transient
	public String getCodeCN8() {
		return code.substring(0, 8);
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSunit() {
		return sunit;
	}
	public void setSunit(String sunit) {
		this.sunit = sunit;
	}
	public String getSunitDesc() {
		return sunitDesc;
	}
	public void setSunitDesc(String sunitDesc) {
		this.sunitDesc = sunitDesc;
	}
	public String getEnglishDesc() {
		return englishDesc;
	}
	public void setEnglishDesc(String englishDesc) {
		this.englishDesc = englishDesc;
	}
	public String getFrenchDesc() {
		return frenchDesc;
	}
	public void setFrenchDesc(String frenchDesc) {
		this.frenchDesc = frenchDesc;
	}
	public String getGermanDesc() {
		return germanDesc;
	}
	public void setGermanDesc(String germanDesc) {
		this.germanDesc = germanDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Favorito))
			return false;
		Favorito other = (Favorito) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}


}
