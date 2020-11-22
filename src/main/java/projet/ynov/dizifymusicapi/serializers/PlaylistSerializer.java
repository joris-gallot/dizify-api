package projet.ynov.dizifymusicapi.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Playlist;
import projet.ynov.dizifymusicapi.entity.Title;

public class PlaylistSerializer extends StdSerializer<Playlist> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlaylistSerializer() {
        this(null);
    }
  
    public PlaylistSerializer(Class<Playlist> playlist) {
        super(playlist);
    }
 
    @Override
    public void serialize(Playlist playlist, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", playlist.getId());
        jgen.writeStringField("name", playlist.getName());
        jgen.writeStringField("updatedAt", sdf.format(playlist.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(playlist.getCreatedAt()));

	    // Set list of titles
	    jgen.writeFieldName("titles");
	    jgen.writeStartArray();
	    if (playlist.getTitles() != null) {
	      for (Title title : playlist.getTitles()) {
	    	   jgen.writeStartObject();
	           jgen.writeNumberField("id", title.getId());
	           jgen.writeStringField("name", title.getName());
	           jgen.writeStringField("duration", title.getDuration().toString());
	           jgen.writeStringField("updatedAt", sdf.format(title.getUpdatedAt()));
	           jgen.writeStringField("createdAt", sdf.format(title.getCreatedAt()));
	           

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
	     jgen.writeEndArray();
        
    	jgen.writeEndObject();
    }
}