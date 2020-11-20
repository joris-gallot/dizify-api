package projet.ynov.dizifymusicapi;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.entity.Album;
import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.Playlist;
import projet.ynov.dizifymusicapi.entity.Title;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.AlbumParams;
import projet.ynov.dizifymusicapi.entity.params.ArtistParams;
import projet.ynov.dizifymusicapi.entity.params.FavoriteParams;
import projet.ynov.dizifymusicapi.entity.params.PlaylistParams;
import projet.ynov.dizifymusicapi.entity.params.TitleParams;
import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;
import projet.ynov.dizifymusicapi.repositories.AlbumRepository;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;
import projet.ynov.dizifymusicapi.repositories.FavoriteRepository;
import projet.ynov.dizifymusicapi.repositories.PlaylistRepository;
import projet.ynov.dizifymusicapi.repositories.TitleRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

@Component
public class DbInit {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TitleRepository titleRepository;
    
    @Autowired
    private ArtistRepository artistRepository;
    
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PlaylistRepository playlistRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private final int MAX_ARTISTS = 2;
    private final int MAX_ALBUMS = 3;
    private final int MAX_TITLES = 5;
    
    @PostConstruct
    public void init() throws ParseException {
    	if (adminRepository.findByUsername("admin") == null) {
    		Faker faker = new Faker();
    		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    		Random rd = new Random();

    		// CREATE ADMIN
    		UserParams adminParams = new UserParams("https://i.pravatar.cc/200", "admin@gmail.com", "admin", passwordEncoder.encode("admin"), new Date(), new Date());
    		Admin admin = new Admin(adminParams);
    		
    		adminRepository.save(admin);
    		
    		// CREATE USER 
    		UserParams userParams = new UserParams("https://i.pravatar.cc/200", "user@gmail.com", "user", passwordEncoder.encode("user"), new Date(), new Date());
    		User user = new User(userParams);
    		User userCreated = userRepository.save(user);
    		
    		// CREATE ARTITS
    		for (int i = 0; i < MAX_ARTISTS; i++) {
    			ArtistParams artistParams = new ArtistParams(faker.artist().name(), "https://i.pravatar.cc/200", faker.lorem().paragraph(), new Date(), new Date());
    			Artist artist = new Artist(artistParams);
    			Artist artistCreated = artistRepository.save(artist);
    			
    			if (rd.nextBoolean()) {
    				FavoriteParams favoriteParams = new FavoriteParams(new Date(), new Date());
    				Favorite favorite = new Favorite(favoriteParams);
    				favorite.setUser(userCreated);
    				favorite.setArtist(artistCreated);    
    				favoriteRepository.save(favorite);
    			}
    			
    			// CREATE ALBUMS
    			for (int j = 0; j < MAX_ALBUMS; j++) {
    				AlbumParams albumParams = new AlbumParams(faker.name().title(), "https://i.pravatar.cc/200", faker.date().birthday(), new Date(), new Date());
    				Album album = new Album(albumParams);
    				album.setAuthor(artistCreated);
    				Album albumCreated = null;
    				
    				if (albumRepository.findByName(album.getName()) == null) {        					
        				albumCreated = albumRepository.save(album);
    				}
    				
    				if (albumCreated != null && rd.nextBoolean()) {
        				FavoriteParams favoriteParams = new FavoriteParams(new Date(), new Date());
        				Favorite favorite = new Favorite(favoriteParams);
        				favorite.setUser(userCreated);
        				favorite.setAlbum(albumCreated);        
        				favoriteRepository.save(favorite);				
        			}
    				
    				Playlist playlist = null;
    				List<Title> titles = new ArrayList<Title>();
    				if (rd.nextBoolean()) {    					
    					PlaylistParams favoriteParams = new PlaylistParams(faker.funnyName().name(), new Date(), new Date());
    					playlist = new Playlist(favoriteParams);
    					playlist.setUser(userCreated);
    				}
    				
    				// CREATE TITLES
    				for (int k = 0; k < MAX_TITLES; k++) {      				
    					int minutes = rd.nextInt(9);
    					int seconds = rd.nextInt(59);
    					
        				Time duration = new Time(formatter.parse("00:0" + minutes + ":" + seconds).getTime()); 
        				TitleParams titleParams = new TitleParams(faker.funnyName().name(), duration, new Date(), new Date());
        				Title title = new Title(titleParams);
        				title.setAuthor(artist);
        				title.setAlbum(albumCreated != null && rd.nextBoolean() ? albumCreated : null);
        				
        				Title titleCreated = null;

        				if (titleRepository.findByName(title.getName()) == null) {        					
        					titleCreated = titleRepository.save(title);
        					titles.add(titleCreated);
        				}
        				
        				if (titleCreated != null && rd.nextBoolean()) {
            				FavoriteParams favoriteParams = new FavoriteParams(new Date(), new Date());
            				Favorite favorite = new Favorite(favoriteParams);
            				favorite.setUser(userCreated);
            				favorite.setTitle(titleCreated);
            				favoriteRepository.save(favorite);   				
            			}
        				

        				if (playlist != null && k == MAX_TITLES - 1) {
            				playlist.setTitles(new HashSet<Title>(titles));
            				playlistRepository.save(playlist);
        				}
        			}	
    			}
    			
    		}
    	}
    }
}