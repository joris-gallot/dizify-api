package projet.ynov.dizifymusicapi;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import projet.ynov.dizifymusicapi.entity.Title;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.AlbumParams;
import projet.ynov.dizifymusicapi.entity.params.ArtistParams;
import projet.ynov.dizifymusicapi.entity.params.FavoriteParams;
import projet.ynov.dizifymusicapi.entity.params.TitleParams;
import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;
import projet.ynov.dizifymusicapi.repositories.AlbumRepository;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;
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
    private PasswordEncoder passwordEncoder;
    
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
    		for (int i = 0; i < 2; i++) {
    			ArtistParams artistParams = new ArtistParams(faker.artist().name(), "https://i.pravatar.cc/200", faker.lorem().paragraph(), new Date(), new Date());
    			Artist artist = new Artist(artistParams);
    			Artist artistCreated = artistRepository.save(artist);
    			
    			// CREATE ALBUMS
    			for (int j = 0; j < 3; j++) {
    				AlbumParams albumParams = new AlbumParams(faker.name().title(), "https://i.pravatar.cc/200", faker.date().birthday() ,new Date(), new Date());
    				Album album = new Album(albumParams);
    				album.setAuthor(artistCreated);
    				Album albumCreated = null;
    				
    				if (albumRepository.findByName(album.getName()) == null) {        					
        				albumCreated = albumRepository.save(album);
    				}
    				
    				// CREATE TITLES
    				for (int k = 0; k < 5; k++) {
    					int minutes = rd.nextInt(9);
    					int seconds = rd.nextInt(59);
    					
        				Time duration = new Time(formatter.parse("00:0" + minutes + ":" + seconds).getTime()); 
        				TitleParams titleParams = new TitleParams(faker.funnyName().name(), duration, new Date(), new Date());
        				Title title = new Title(titleParams);
        				title.setAuthor(artist);
        				title.setAlbum(albumCreated != null && rd.nextBoolean() ? albumCreated : null);
        				
        				if (titleRepository.findByName(title.getName()) == null) {        					
        					titleRepository.save(title);
        				}
        			}	
    			}
    			
    		}
    	}
    }
}