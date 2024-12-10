package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {

    //Logger Instanz für Ausgabe von Debug Infos
    private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);
    
    //injektion UserService um Benutzer zu erstellen
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create user admin");
        //User Initialisieren -> Konstruktor User -> User created without values, anschließend über Setter-Methoden Werte zuordnen
        User user = new User();
        user.setRole("ADMIN");
        user.setFirstName("Administrator");
        user.setLastName("Administrator");
        user.setEmail("admin@example.com");
        user.setPassword("extremeSecurePassword1234");
        userService.createUser(user);

        log.debug("create user alice");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Alice");
        user.setLastName("Adams");
        user.setEmail("alice@example.com");
        user.setPassword("alice1234");
        userService.createUser(user);

        log.debug("create user bob");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Bob");
        user.setLastName("Builder");
        user.setEmail("bob@example.com");
        user.setPassword("bob1234");
        userService.createUser(user);

        log.debug("### Data initialized ###");
    }

}
