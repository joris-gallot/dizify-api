package projet.ynov.dizifymusicapi.entity.params;

import java.util.Date;

public class UserParams {
    private long id;
	
    private String name;

    private String image;

    private String email;
    
    private String username;
    
    private String password;

    private Date createdAt;

    private Date updatedAt;

	public UserParams(String image, String email, String username, String password, Date createdAt,
			Date updatedAt) {
		super();
		this.image = image;
		this.email = email;
		this.username = username;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserParams [id=" + id + ", name=" + name + ", image=" + image + ", email=" + email + ", username="
				+ username + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
    
}
