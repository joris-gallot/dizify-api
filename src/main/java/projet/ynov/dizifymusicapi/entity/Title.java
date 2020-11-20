package projet.ynov.dizifymusicapi.entity;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import projet.ynov.dizifymusicapi.entity.params.TitleParams;
import projet.ynov.dizifymusicapi.serializers.TitleSerializer;

@Entity
@Table(name = "titles")
@EntityListeners(AuditingEntityListener.class)
@JsonSerialize(using = TitleSerializer.class)
public class Title {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "name", unique=true, nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @ManyToOne
    private Album album;
    
    @ManyToOne
    private Artist author;
    
    @ManyToMany(mappedBy="titles")
    private Set<Playlist> playlists;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private long favoriteId;
    
    public Title() {
    	super();
    }
    
    public Title(TitleParams params) {
		this.id = params.getId();
		this.name = params.getName();
		this.duration = params.getDuration();
		this.createdAt = params.getCreatedAt();
		this.updatedAt = params.getUpdatedAt();
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

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
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

	public Artist getAuthor() {
		return author;
	}

	public void setAuthor(Artist author) {
		this.author = author;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	public Set<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}

	public long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(long favoriteId) {
		this.favoriteId = favoriteId;
	}

	@Override
	public String toString() {
		return "Title [id=" + id + ", name=" + name + ", duration=" + duration + ", album=" + album + ", author="
				+ author + ", playlists=" + playlists + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
}
