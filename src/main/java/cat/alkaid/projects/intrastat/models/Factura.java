package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@Entity
public class Factura {
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    private String codigo;

    private String entrega;
    
    private int transaccion;
    
    private int regimen;
    
    private String flujo;
    
    @Temporal(TemporalType.DATE)
    private Date presentado;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
	private Transporte transporte;

    @ManyToOne
	private Pais origen;

    @ManyToOne
	private Pais pais;

    @ManyToOne
	private Provincia provincia;

    @ManyToOne
	private Proveedor proveedor;

    @ManyToOne
	private Account account;
    
    @ManyToOne
	private Company company;

	@ManyToOne
	private Periodo periodo;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "factura_id")
	private Set<Material> materiales = new HashSet<>();
    
	
	//Estado miembro de procedencia/destino(A2)
	//Provincia de destino/origen(N2)
	//Condiciones de entrega(A3)
	//Naturaleza de la Transacción(N2)
	//Modalidad de Transporte(N1)
	//Puerto/Aeropuerto de Carga o Descarga(N4)
	//Código de las mercancias(N8)
	//País de Origen(A2)
	//Régimen estadístico(N1)
	//Masa Neta en Kg.(N12 ó N12,3)
	//Unidades Suplementarias(N12 ó N12,3)
	//Importe Facturado(N13,2)
	//Valor Estadístico(N13,2)
	//Ex: FR;31;FOB;11;3;;85182190;CN;1;115;162;15,37;15,37
		//IT; 8;EXW;11;2;;38130000;ES;1;000;000;00000;00000

	public List<String> toCSV(char delimeter, char endRecord){
		return null;
	}
			/*
	public List<String> toCSV(char delimeter, char endRecord){
		List<String> output = new ArrayList<String>();
		
		for(Material mat : materiales){
			String line = String.format(Locale.FRANCE, "%2s%1c%2s%1c%3s%1c%2d%1c%d%1c%1c%8s%1c%2s%1c%1d%1c%d%1c%d%1c%.2f%1c%.2f%c",
					pais.getSigla(),delimeter,
					"08", delimeter,
					entrega, delimeter,
					getTransaccion(), delimeter, 
					Integer.parseInt(transporte.getCodigo()), 
					delimeter ,delimeter, 
					mat.getNomenclature().getCodeCN8(), delimeter, 
					"ES", delimeter, 
					getRegimen(), delimeter, 
					mat.getPeso(),delimeter,
					mat.getUnidades(), delimeter,
					mat.getPrice(), delimeter, 
					mat.getVestadistico(), endRecord );
			
			
			output.add(line);
		}
		
		return output;
	}*/

}