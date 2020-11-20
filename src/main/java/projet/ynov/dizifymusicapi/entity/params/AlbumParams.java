package projet.ynov.dizifymusicapi.entity.params;

import java.util.Date;

public class AlbumParams {
    private long id;
	
    private String name;

    private String image;
    
    private Date publicationDate;

    private long author_id;

    private Iterable<Long> title_ids;

    private Date createdAt;

    private Date updatedAt;
    
    public AlbumParams() {
    	super();
    }

	public AlbumParams(String name, String image, Date publicationDate, Date createdAt, Date updatedAt) {
		super();
		this.name = name;
		this.image = image;
		this.publicationDate = publicationDate;
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

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
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

	public long getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(long author_id) {
		this.author_id = author_id;
	}

	public Iterable<Long> getTitle_ids() {
		return title_ids;
	}

	public void setTitle_ids(Iterable<Long> title_ids) {
		this.title_ids = title_ids;
	}
	    
}
