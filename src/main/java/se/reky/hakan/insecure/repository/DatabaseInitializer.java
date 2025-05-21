package se.reky.hakan.insecure.repository;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.reky.hakan.insecure.model.User;

//Initialiserar databasen med lite data, se nedan
@Component
public class DatabaseInitializer {


    private UserRepository userRepository;

    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Denna metod skapar två users och insertar dem i databasen
    @PostConstruct//Annotationen gör att metoden körs vid uppstart av appen
    public void init() {
        //admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("adminpass");
        //vanlig user
        User user = new User();
        user.setUsername("user");
        user.setPassword("userpass");

        userRepository.save(admin);
        userRepository.save(user);
    }
}

