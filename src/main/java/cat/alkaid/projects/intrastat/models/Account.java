package cat.alkaid.projects.intrastat.models;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.PostLoad;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.Temporal;

import java.util.Collection;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Embedded;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;

@Entity
@XmlRootElement
@JsonIgnoreProperties(value = {"authorities"})
public class Account implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "userName")
    private String username;
    private String password;
    private String token;
    private String activationCode;
    @Transient
    private Long activeCompany;
    @Transient
    private String plainPwd;
    @Embedded
    private Address address;
    @Embedded
    @JsonUnwrapped
    private Person person;
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "account")
    private Set<Company> companies;
    @Temporal(TemporalType.DATE)
    private Date activated;
    
    public Account() {
        this.companies = new HashSet<Company>();
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        final Account other = (Account)obj;
        return this.id == null || this.id.equals(other.id);
    }
    
    public String getActivationCode() {
        return this.activationCode;
    }
    
    public void setActivationCode(final String activationCode) {
        this.activationCode = activationCode;
    }
    
    public void changePassword(final String password) {
        this.setPlainPwd(password);
    }
    
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
    
    public Date getActivated() {
        return this.activated;
    }
    
    public void setActivated(final Date activated) {
        this.activated = activated;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String userName) {
        this.username = userName;
    }
    
    public boolean validate(final String password) {
        return this.plainPwd.equals(password);
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(final Address address) {
        this.address = address;
    }
    
    public Person getPerson() {
        return this.person;
    }
    
    public void setPerson(final Person person) {
        this.person = person;
    }
    
    public Set<Company> getCompanies() {
        return this.companies;
    }
    
    public void setCompanies(final Set<Company> companies) {
        this.companies = companies;
    }
    
    @JsonIgnore
    public String getPlainPwd() {
        return this.plainPwd;
    }
    
    public void setPlainPwd(final String plainPwd) {
        this.plainPwd = plainPwd;
        this.password = this.encryptPassword(plainPwd);
    }
    
    @PostLoad
    public void onPostLoad() {
/*         if (this.password != null) {
            this.plainPwd = this.decryptPassword(this.password);
        }
 */    }
    
    private String encryptPassword(final String text) {
        try {
            final SecretKeySpec key = new SecretKeySpec("0123456789abcdef".getBytes("UTF-8"), "AES");
            final Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(1, key);
            return DatatypeConverter.printBase64Binary(c.doFinal(text.getBytes("UTF-8")));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private String decryptPassword(final String text) {
        try {
            final SecretKeySpec key = new SecretKeySpec("0123456789abcdef".getBytes("UTF-8"), "AES");
            final Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(2, key);
            return new String(c.doFinal(DatatypeConverter.parseBase64Binary(text)), "UTF-8");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Long getActiveCompany() {
        return this.activeCompany;
    }
    
    public void setActiveCompany(final Long activeCompany) {
        this.activeCompany = activeCompany;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

