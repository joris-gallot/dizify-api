package projet.ynov.dizifymusicapi.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import projet.ynov.dizifymusicapi.entity.Album;
import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Title;

public class AlbumSerializer extends StdSerializer<Album> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlbumSerializer() {
        this(null);
    }
  
    public AlbumSerializer(Class<Album> album) {
        super(album);
    }
 
    @Override
    public void serialize(Album album, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", album.getId());
        jgen.writeStringField("name", album.getName());
        jgen.writeStringField("image", album.getImage());
        jgen.writeBooleanField("isFavorite", album.isFavorite());
        jgen.writeStringField("publicationDate", album.getPublicationDate().toString());
        jgen.writeStringField("updatedAt", sdf.format(album.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(album.getCreatedAt()));
        
    	// Set list of titles
		jgen.writeFieldName("titles");
        jgen.writeStartArray();
        if (album.getTitles() != null) {
	        for (Title title : album.getTitles()) {
	        	jgen.writeStartObject();
	            jgen.writeNumberField("id", title.getId());
	            jgen.writeStringField("name", title.getName());
	            jgen.writeStringField("duration", title.getDuration().toString());
	            jgen.writeStringField("updatedAt", sdf.format(title.getUpdatedAt()));
	            jgen.writeStringField("createdAt", sdf.format(title.getCreatedAt()));
	            jgen.writeEndObject();
	        }
    	}
        jgen.writeEndArray();
        
    	// Set author
        Artist author = album.getAuthor();
    	jgen.writeFieldName("author");
    	jgen.writeStartObject();
        jgen.writeNumberField("id", author.getId());
        jgen.writeStringField("name", author.getName());
        jgen.writeStringField("updatedAt", sdf.format(author.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(author.getCreatedAt()));
        jgen.writeEndObject();
        
    	jgen.writeEndObject();
    }
}