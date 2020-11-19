package projet.ynov.dizifymusicapi.serializers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.enums.Role;

public class AdminSerializer extends StdSerializer<Admin> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminSerializer() {
        this(null);
    }
  
    public AdminSerializer(Class<Admin> admin) {
        super(admin);
    }
 
    @Override
    public void serialize(Admin admin, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", admin.getId());
        jgen.writeStringField("username", admin.getUsername());
        jgen.writeStringField("email", admin.getEmail());
        jgen.writeStringField("image", admin.getImage());
        jgen.writeStringField("role", Role.ROLE_ADMIN.toString());
        jgen.writeStringField("updatedAt", sdf.format(admin.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(admin.getCreatedAt()));
        
        if (admin.getToken() != null) {
        	jgen.writeStringField("token", admin.getToken());        	
        }
             
    	jgen.writeEndObject();
    }
}