package cat.alkaid.projects.intrastat.models;

import javax.persistence.*;

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
