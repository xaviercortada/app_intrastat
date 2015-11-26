package cat.alkaid.projects.intrastat.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Factura implements Serializable {
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    private String codigo;

    private String entrega;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
	private Transporte transporte;

    @ManyToOne
	private Pais pais;

    @ManyToOne
	private Proveedor proveedor;

    @ManyToOne
	private Account account;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="factura")
	private Set<Material> materiales = new HashSet<Material>();
    
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Transporte getTransporte() {
		return transporte;
	}
	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Set<Material> getMateriales() {
		return materiales;
	}
	public void setMateriales(Set<Material> materiales) {
		this.materiales = materiales;
	}
	
	public void addMaterial(Material mat) {
		materiales.add(mat);
		
	}
	

}
