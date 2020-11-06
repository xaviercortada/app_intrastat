package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



@SuppressWarnings("serial")
@Entity
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
@JsonIgnoreProperties("factura")
public class Material implements Serializable{
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    private String codigo;
    private String entrega;
    @ManyToOne
	private Nomenclature nomenclature;
    private Integer peso;
    private String importe;
    private Float price;
    private Integer unidades;
    private String name;
    private Float vestadistico;
    
    @ManyToOne
    @XmlTransient
    private Factura factura;
    
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
	public String getEntrega() {
		return entrega;
	}
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}
	public Nomenclature getNomenclature() {
		return nomenclature;
	}
	public void setNomenclature(Nomenclature nomenclature) {
		this.nomenclature = nomenclature;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
    }
	public Integer getUnidades() {
		return unidades;
	}
	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public Float getVestadistico() {
		return vestadistico;
	}
	public void setVestadistico(Float vestadistico) {
		this.vestadistico = vestadistico;
	}


}
