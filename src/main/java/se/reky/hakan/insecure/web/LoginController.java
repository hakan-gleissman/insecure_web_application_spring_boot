package se.reky.hakan.insecure.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.reky.hakan.insecure.repository.UserRepository;

/*
Denna klass är öppen för SQL-injection eftersom input från användaren
används direkt i SQL-frågan. Försök att logga in som användare admin
utan att skriva rätt lösenord!
Inloggningsuppgifter kan hittas i klassen DatabaseInitializer

Tips: använd en SQL-kommentar för att kommentera bort denna del
av SQL-frågan:
    AND password=
En SQL-kommentar ser ut så här: --
Din fråga behöver få detta format:
SELECT * FROM users WHERE username = 'admin' --AND password = 'vad_som_helst'

 Därefter är din uppgift att förhindra att SQL-injection kan användas.
 Tips: använd istället klassen UserRepository som finns i denna applikation.

 NOT: Repository-klasser kan injiceras som vanligt trots att de inte är annoterade.

 Du kan returnera hela User-objektet istället för endast en String, som
 metoden nu gör.

 Extrauppgift: endpoint-metoden login är öppen för sk Brute force-attack.
 Man kan försöka logga in med fel uppgifter hur många gånger som helst.
 Använd HttpSessions-klassen (är redan nu parameter till metoden login)
 för att hålla reda på antal inloggningsförsök.
 Tips: använd session.setAttribute och session.getAttribute för att skapa
 en counter. Returnera strängen "Accoount is locked" när tre felaktiga inloggningsförsök
 har gjorts.

 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private final JdbcTemplate jdbcTemplate;
    private static final String LOGIN_ATTEMPTS_KEY ="login_attempts";

    private UserRepository userRepository;

    public LoginController(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;

        this.userRepository = userRepository;
    }

    @GetMapping//getmapping för enkelhets skull
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        //Denna typ av query är öppen för SQL-injection
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            String result = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                return rs.getString("username");
            });
            return result + " logged in!";

        } catch (Exception e) {
            return "Login failed";
        }

    }
}

