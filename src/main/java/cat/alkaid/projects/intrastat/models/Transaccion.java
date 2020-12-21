package cat.alkaid.projects.intrastat.models;

import javax.persistence.*;

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
