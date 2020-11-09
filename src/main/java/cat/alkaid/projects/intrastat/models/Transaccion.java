package cat.alkaid.projects.intrastat.models;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * Entity implementation class for Entity: Transaccion
 *
 */

@Data
@Entity
public class Transaccion {

	@Id
	private int id;
    private String codigo;
    private String texto;
    private int grupo;

}
