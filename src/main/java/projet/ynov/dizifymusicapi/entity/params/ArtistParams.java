package projet.ynov.dizifymusicapi.entity.params;

import java.util.Date;

public class ArtistParams {
    private long id;
	
    private String name;

    private String image;
    
    private String description;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    public ArtistParams() {
    	super();
    }
    
	public ArtistParams(String name, String image, String description, Date createdAt, Date updatedAt) {
		super();
		this.name = name;
		this.image = image;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
