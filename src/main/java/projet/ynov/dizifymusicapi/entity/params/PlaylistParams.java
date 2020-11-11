package projet.ynov.dizifymusicapi.entity.params;

import java.util.Date;

public class PlaylistParams {

    private long id;
	
    private String name;

    private long user_id; // to delete with auth

    private Iterable<Long> title_ids;

    private Date createdAt;

    private Date updatedAt;

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

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
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

	public Iterable<Long> getTitle_ids() {
		return title_ids;
	}

	public void setTitle_ids(Iterable<Long> title_ids) {
		this.title_ids = title_ids;
	}
    
}
