package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;


@Data
@Entity
@JsonIgnoreProperties("account")
public class User {
	
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
    
}

