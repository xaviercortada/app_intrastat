package cat.alkaid.projects.intrastat.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
    private String domicilio;
    private String poblacion;
    private String provincia;
    private String cpostal;
    private String pais;
    
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getCpostal() {
		return cpostal;
	}
	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
    
    

}
