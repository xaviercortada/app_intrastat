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

import lombok.Data;




@Data
@Entity
public class Material {
	
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
    
}
