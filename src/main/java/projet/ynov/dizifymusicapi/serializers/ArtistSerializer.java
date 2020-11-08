package projet.ynov.dizifymusicapi.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import projet.ynov.dizifymusicapi.entity.Album;
import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Title;

public class ArtistSerializer extends StdSerializer<Artist> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArtistSerializer() {
        this(null);
    }
  
    public ArtistSerializer(Class<Artist> artist) {
        super(artist);
    }
 
    @Override
    public void serialize(Artist artist, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", artist.getId());
        jgen.writeStringField("name", artist.getName());
        jgen.writeStringField("image", artist.getImage());
        jgen.writeStringField("updatedAt", artist.getUpdatedAt().toString());
        jgen.writeStringField("createdAt", artist.getCreatedAt().toString());
        
        // Set list of titles
    	jgen.writeFieldName("titles");
        jgen.writeStartArray();
        if (artist.getTitles() != null) {
	        for (Title title : artist.getTitles()) {
	        	jgen.writeStartObject();
	            jgen.writeNumberField("id", title.getId());
	            jgen.writeStringField("name", title.getName());
	            jgen.writeStringField("duration", title.getDuration().toString());
	            jgen.writeStringField("updatedAt", title.getUpdatedAt().toString());
	            jgen.writeStringField("createdAt", title.getCreatedAt().toString());
	            jgen.writeEndObject();
	        }
        }
        jgen.writeEndArray();

        // Set list of albums
    	jgen.writeFieldName("albums");
        jgen.writeStartArray();
        if (artist.getAlbums() != null) {
	        for (Album album : artist.getAlbums()) {
	        	jgen.writeStartObject();
	            jgen.writeNumberField("id", album.getId());
	            jgen.writeStringField("name", album.getName());
	            jgen.writeStringField("image", album.getImage());
	            jgen.writeStringField("publicationDate", album.getPublicationDate().toString());
	            jgen.writeStringField("updatedAt", album.getUpdatedAt().toString());
	            jgen.writeStringField("createdAt", album.getCreatedAt().toString());
	            jgen.writeEndObject();
	        }
        }
        jgen.writeEndArray();
        
        jgen.writeEndObject();
    }
}