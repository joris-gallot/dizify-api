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
import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.Title;

public class FavoriteSerializer extends StdSerializer<Favorite> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FavoriteSerializer() {
        this(null);
    }
  
    public FavoriteSerializer(Class<Favorite> favorite) {
        super(favorite);
    }
 
    @Override
    public void serialize(Favorite favorite, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
        jgen.writeStartObject();
        jgen.writeNumberField("id", favorite.getId());
        jgen.writeStringField("updatedAt", sdf.format(favorite.getUpdatedAt()));
        jgen.writeStringField("createdAt", sdf.format(favorite.getCreatedAt()));
        
        // Set album
        Album album = favorite.getAlbum();
        if (album != null) {
        	jgen.writeStringField("type", "ALBUM_TYPE");
            jgen.writeNumberField("album_id", album.getId());
            jgen.writeStringField("name", album.getName());
            jgen.writeStringField("publicationDate", album.getPublicationDate().toString());
            jgen.writeStringField("image", album.getImage());
        }
        
        // Set artist
        Artist artist = favorite.getArtist();
        if (artist != null) {
        	jgen.writeStringField("type", "ARTIST_TYPE");
            jgen.writeNumberField("artist_id", artist.getId());
            jgen.writeStringField("name", artist.getName());
            jgen.writeStringField("image", artist.getImage());
        }
        
        // Set titles
        Title title = favorite.getTitle();
        if (title != null) {
        	jgen.writeStringField("type", "TITLE_TYPE");
            jgen.writeNumberField("title_id", title.getId());
            jgen.writeStringField("name", title.getName());
            jgen.writeStringField("duration", title.getDuration().toString());
        }
        
    	jgen.writeEndObject();
    }
}