package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Pais
 *
 */

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Pais implements Serializable {

	@Id
	private int codigo;
    private String name;
    private String sigla;

	public Pais() {
		super();
	}   
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
   
}
