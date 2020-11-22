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

public class TitleSerializer extends StdSerializer<Title> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TitleSerializer() {
        this(null);
    }
  
    public TitleSerializer(Class<Title> title) {
        super(title);
    }
 
    @Override
    public void serialize(Title title, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", title.getId());
        jgen.writeStringField("name", title.getName());
        jgen.writeStringField("duration", title.getDuration().toString());
        jgen.writeStringField("updatedAt", sdf.format(title.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(title.getCreatedAt()));
        
        if (title.getFavoriteId() == 0L) {        	
        	jgen.writeStringField("favoriteId", null);
        } else {
        	jgen.writeNumberField("favoriteId", title.getFavoriteId());
        }
        
    	// Set album
        Album album = title.getAlbum();
        if (album == null) {
        	jgen.writeStringField("album", null);
        } else {
        	jgen.writeFieldName("album");
        	jgen.writeStartObject();
            jgen.writeNumberField("id", album.getId());
            jgen.writeStringField("name", album.getName());
            jgen.writeStringField("publicationDate", album.getPublicationDate().toString());
            jgen.writeStringField("image", album.getImage());
            jgen.writeStringField("updatedAt", sdf.format(album.getUpdatedAt()));
            jgen.writeStringField("createdAt", sdf.format(album.getCreatedAt()));
            jgen.writeEndObject();
        }

    	// Set author
        Artist author = title.getAuthor();
    	jgen.writeFieldName("author");
    	jgen.writeStartObject();
        jgen.writeNumberField("id", author.getId());
        jgen.writeStringField("name", author.getName());
        jgen.writeStringField("image", author.getImage());
        jgen.writeStringField("description", author.getDescription());
        jgen.writeStringField("updatedAt", sdf.format(author.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(author.getCreatedAt()));
        jgen.writeEndObject();

    	jgen.writeEndObject();
    }
}