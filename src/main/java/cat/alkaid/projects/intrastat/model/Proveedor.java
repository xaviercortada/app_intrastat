package cat.alkaid.projects.intrastat.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Proveedor implements Serializable{
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
    private String codigo;
    private String name;
    
    @Embedded
    private Address address;
    
    @Embedded
    private Person person;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



}
