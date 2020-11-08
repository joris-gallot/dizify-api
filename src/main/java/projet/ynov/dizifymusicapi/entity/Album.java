package projet.ynov.dizifymusicapi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import projet.ynov.dizifymusicapi.entity.params.AlbumParams;
import projet.ynov.dizifymusicapi.serializers.AlbumSerializer;

@Entity
@Table(name = "albums")
@JsonSerialize(using = AlbumSerializer.class)
@EntityListeners(AuditingEntityListener.class)
public class Album {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;
    
    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

	@ManyToOne
    private Artist author;
    
    @OneToMany(mappedBy="album", cascade={CascadeType.ALL})
    private Set<Title> titles;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
    
    public Album() {
    	super();
    }
    
    public Album(AlbumParams params) {
		this.id = params.getId();
		this.name = params.getName();
		this.image = params.getImage();
		this.publicationDate = params.getPublicationDate();
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

	public Artist getAuthor() {
		return author;
	}

	public void setAuthor(Artist author) {
		this.author = author;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

}
