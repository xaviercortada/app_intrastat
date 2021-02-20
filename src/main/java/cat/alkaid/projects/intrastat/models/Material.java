package cat.alkaid.projects.intrastat.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude="factura")
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties({ "factura" })
public class Material
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String codigo;
    private String entrega;
    @ManyToOne
    private Nomenclature nomenclature;
    private Integer peso;
    private String importe;
    private BigDecimal price;
    private Integer unidades;
    private String name;
    private BigDecimal vestadistico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    @XmlTransient
    private Factura factura;
    
   
    @PreUpdate
    @PrePersist
    private void setPriceUpdate() {
        try {
            this.price = new BigDecimal(this.importe);
            final BigDecimal discountPercent = new BigDecimal("0.03");
            if (this.factura.getEntrega().equalsIgnoreCase("EXW")) {
                this.vestadistico = this.price.add(this.price.multiply(discountPercent));
            }
            else {
                this.vestadistico = this.price.subtract(this.price.multiply(discountPercent));
            }
        }
        catch (Exception e) {
            this.price = new BigDecimal("0");
            this.vestadistico = new BigDecimal("0");
        }
    }
}

