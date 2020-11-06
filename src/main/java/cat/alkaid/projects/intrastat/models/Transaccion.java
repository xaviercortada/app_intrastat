package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Transaccion
 *
 */

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Transaccion implements Serializable {

	@Id
	private int id;
    private String codigo;
    private String texto;
    private int grupo;

	public Transaccion() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}   
   
}
