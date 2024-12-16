package edu.fra.uas.exfourrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.exfourrest.service.GradeService;

@SpringBootApplication
public class ExfourrestApplication {

	private static final Logger log = LoggerFactory.getLogger(ExfourrestApplication.class);

	@Autowired
	private GradeService gradeService;

	public static void main(String[] args) {
		SpringApplication.run(ExfourrestApplication.class, args);
	}

	@Bean
    CommandLineRunner init() {
        return args -> {
            log.info("Anwendung gestartet - Testausgabe der Grades:");

            // Testdaten ausgeben
            gradeService.getGrades().forEach((subject, grade) -> 
                log.info("Fach: {}, Note: {}", subject, grade)
            );

            // Durchschnitt berechnen und ausgeben
            double average = gradeService.calculateAverage();
            log.info("Durchschnittsnote: {}", average);
        };
    }

}
