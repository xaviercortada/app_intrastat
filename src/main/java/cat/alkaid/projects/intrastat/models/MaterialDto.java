package cat.alkaid.projects.intrastat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by xavier on 27/09/15.
 */

public class MaterialDto implements Comparable {

    private String codFactura;

    private String codCategory;

    private String nameCategory;

    private String siglas;

    private String codPais;

    private String codPaisOrigen;
    
    private String proveedor;

    private String entrega;

    private Integer peso;

    private String importe;

    private Integer unidades;

    private String name;
    
    private int codTransaccion;
    
    private String codTransporte;
    
    private int codRegimen;

    private Float price;
    
    private Float vestadistico;

    public MaterialDto(Factura factura, Material material){
        setCodCategory(material.getNomenclature().getCodeCN8().toString());
        setCodFactura(factura.getCodigo());
        setEntrega(material.getEntrega());
        setImporte(material.getImporte());
        setPrice(material.getPrice());
        setNameCategory(material.getNomenclature().getDescription());
        setPeso(material.getPeso());
        setUnidades(material.getUnidades());
        setSiglas(factura.getPais().getSigla());
        setEntrega(factura.getEntrega());
        setProveedor(factura.getProveedor().getName());
        
        setCodRegimen(factura.getRegimen());
        setCodTransaccion(factura.getTransaccion());
        setCodTransporte(factura.getTransporte().getCodigo());
        
        setVestadistico(material.getVestadistico());
        
        //Pais Origen
        setCodPaisOrigen("");
        if(factura.getOrigen() != null){
            setCodPaisOrigen(String.format("%02d", factura.getOrigen().getCodigo()));        	
        }
        
        //Pais procedencia
        setCodPais("");
        if(factura.getPais() != null){
            setCodPais(String.format("%02d", factura.getPais().getCodigo()));        	
        }

        setCodPaisOrigen("");
        if(factura.getOrigen() != null){
            setCodPaisOrigen(String.format("%02d", factura.getOrigen().getCodigo()));        	
        }
    }

    public String getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(String codFactura) {
        this.codFactura = codFactura;
    }

    public String getCodCategory() {
        return codCategory;
    }

    public void setCodCategory(String codCategory) {
        this.codCategory = codCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
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

    public void setPrice(Float price) {
    	this.price = price;
    }

    public Float getPrice() {
    	return price;
//    	try{
//    		String p = this.importe.replace(".", "");
//    		p = p.replace(",", ".");
//    		return Float.parseFloat(p);
//    	}catch(Exception e){
//    		return (float) 0;
//    	}
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

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public Float getVestadistico() {
		return vestadistico;
	}

	public void setVestadistico(Float vestadistico) {
		this.vestadistico = vestadistico;
	}

	public String getKey(){
        return String.format("%s%s%s", getCodCategory(),
                getEntrega(), getCodPais());
    }
	
	public String toCSV(char delimeter, char endRecord){
		
			String line = String.format(Locale.FRANCE, "%2s%1c%2s%1c%3s%1c%2d%1c%d%1c%1c%8s%1c%s%1c%1d%1c%d%1c%d%1c%.2f%1c%.2f%c",
					siglas,delimeter,
					"08", delimeter,
					entrega, delimeter,
					codTransaccion, delimeter, 
					Integer.parseInt(codTransporte), 
					delimeter ,delimeter, 
					codCategory, delimeter, 
					codPaisOrigen, delimeter, 
					codRegimen, delimeter, 
					getPeso(),delimeter,
					unidades, delimeter,
					price, delimeter, 
					vestadistico, endRecord );
			
			
		
		return line;
	}


    @Override
    public int compareTo(Object o) {
        MaterialDto dto = (MaterialDto)o;
        return this.getKey().compareTo(dto.getKey());
    }

	public String getCodTransporte() {
		return codTransporte;
	}

	public void setCodTransporte(String codTransporte) {
		this.codTransporte = codTransporte;
	}

	public int getCodTransaccion() {
		return codTransaccion;
	}

	public void setCodTransaccion(int codTransaccion) {
		this.codTransaccion = codTransaccion;
	}

	public int getCodRegimen() {
		return codRegimen;
	}

	public void setCodRegimen(int codRegimen) {
		this.codRegimen = codRegimen;
	}

	public String getCodPaisOrigen() {
		return codPaisOrigen;
	}

	public void setCodPaisOrigen(String codPaisOrigen) {
		this.codPaisOrigen = codPaisOrigen;
	}

}
