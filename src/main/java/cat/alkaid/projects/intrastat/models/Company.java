package cat.alkaid.projects.intrastat.models;

import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;

@Data
@Entity
@XmlRootElement
@JsonIgnoreProperties({ "account" })
public class Company
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    private String name;
    private String email;
    private String phone;
    @ManyToOne
    @XmlTransient
    private Account account;
    @Embedded
    private Address address;
    
}