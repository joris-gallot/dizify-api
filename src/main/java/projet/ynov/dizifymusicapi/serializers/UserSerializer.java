package projet.ynov.dizifymusicapi.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import projet.ynov.dizifymusicapi.entity.Playlist;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.enums.Role;

public class UserSerializer extends StdSerializer<User> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserSerializer() {
        this(null);
    }
  
    public UserSerializer(Class<User> user) {
        super(user);
    }
 
    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", user.getId());
        jgen.writeStringField("username", user.getUsername());
        jgen.writeStringField("email", user.getEmail());
        jgen.writeStringField("image", user.getImage());
        jgen.writeStringField("role", Role.ROLE_USER.toString());
        jgen.writeStringField("updatedAt", sdf.format(user.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(user.getCreatedAt()));
        
        if (user.getToken() != null) {
        	jgen.writeStringField("token", user.getToken());        	
        }
        
        // Set list of playlists
 		jgen.writeFieldName("playlists");
        jgen.writeStartArray();
        if (user.getPlaylists() != null) {
 	       for (Playlist playlist : user.getPlaylists()) {
 	    	   jgen.writeStartObject();
 	           jgen.writeNumberField("id", playlist.getId());
 	           jgen.writeStringField("name", playlist.getName());
 	           jgen.writeStringField("updatedAt", sdf.format(playlist.getUpdatedAt()));
 	           jgen.writeStringField("createdAt", sdf.format(playlist.getCreatedAt()));
 	           jgen.writeEndObject();
 	        }
     	}
        jgen.writeEndArray();
             
    	jgen.writeEndObject();
    }
}