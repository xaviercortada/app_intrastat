package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * Entity implementation class for Entity: Pais
 *
 */

@Data
@Entity
public class Pais {

	@Id
	private int codigo;
    private String name;
    private String sigla;
   
}
