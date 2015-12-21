package cat.alkaid.projects.intrastat.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonUnwrapped;


@SuppressWarnings("serial")
@Entity
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
@JsonIgnoreProperties("account")
public class User implements Serializable {
	
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
    @Embedded
    private Address address;
    
//    @JsonSerialize(using = MyUserSerializer.class)
    
    @Embedded
    @JsonUnwrapped
    private Person person;
    
    @OneToOne
    @JoinColumn(name = "account_id")
    @XmlTransient
    private Account account;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	

}

