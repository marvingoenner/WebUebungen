package edu.fra.uas.Rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.Rest.controller.GradeController;

@SpringBootApplication
public class RestApplication {

	private static final Logger log = LoggerFactory.getLogger(RestApplication.class);

	@Autowired
	private GradeController gradeController;

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		CommandLineRunner action = new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				log.debug(gradeController.putMessage("Hello World"));

				log.debug("Grades:\n"+gradeController.printGrades());

				double average = gradeController.calculateAverage();
				log.debug("Average grade: "+average);
			}
		};
		return action;
	}

}

