package projet.ynov.dizifymusicapi.entity.params;

import java.util.Date;

public class FavoriteParams {
    private long id;
    
    private long album_id;
    private long title_id;
    private long artist_id;

    private Date createdAt;

    private Date updatedAt;
    
    public FavoriteParams() {
    	super();
    }
    
	public FavoriteParams(Date createdAt, Date updatedAt) {
		super();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(long album_id) {
		this.album_id = album_id;
	}

	public long getTitle_id() {
		return title_id;
	}

	public void setTitle_id(long title_id) {
		this.title_id = title_id;
	}

	public long getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(long artist_id) {
		this.artist_id = artist_id;
	}

	@Override
	public String toString() {
		return "FavoriteParams [id=" + id + ", album_id=" + album_id + ", title_id=" + title_id
				+ ", artist_id=" + artist_id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
	
}
