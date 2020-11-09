package cat.alkaid.projects.intrastat.models;

import javax.persistence.Entity;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name="account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@OneToOne(mappedBy = "account")
	private
	User user;
	
	private String userName;
    private String password;
    private String token;

    @Temporal(TemporalType.DATE)
    private Date activated;

	public void changePassword(String password2) {
	}

}