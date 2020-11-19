package projet.ynov.dizifymusicapi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.serializers.AdminSerializer;

@Entity
@Table(name = "admins")
@JsonSerialize(using = AdminSerializer.class)
@EntityListeners(AuditingEntityListener.class)
public class Admin {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    @Column(name = "email", unique=true, nullable = false)
    private String email;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "username", unique=true, nullable = false)
    private String username;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String token;
    
    public Admin() {
    	super();
    }
    
    public Admin(UserParams params) {
		this.id = params.getId();
		this.username = params.getUsername();
		this.image = params.getImage();
		this.password = params.getPassword();
		this.email = params.getEmail();
		this.createdAt = params.getCreatedAt();
		this.updatedAt = params.getUpdatedAt();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
	
    
}
