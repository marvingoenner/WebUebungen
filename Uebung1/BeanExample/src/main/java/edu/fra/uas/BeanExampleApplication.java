package edu.fra.uas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeanExampleApplication {

	//Einstiegspunkt der Anwendung, ausgeführt von JVM, 
	public static void main(String[] args) {
		//Startet die Anweundung, indem sie IOC-Container erstellt, Beans initialisiert, etc. 
		SpringApplication.run(BeanExampleApplication.class, args); //startet Hauptklasse -> Klasse mit Annotation SpringBootApplication
	}

	//Bean Container werden nach dem Start der Anwendung unmittelbar ausgeführt
	@Bean
	//CommandLineRunner ist ein Interface von SpringBoot, dass die Methode run bereitstellt
	CommandLineRunner init() {
		//CommandLineRunner wird als Bean erkannt und nach Start der Anwendung automatisch ausgeführt
		CommandLineRunner action = new CommandLineRunner() {
			@Override //Methode wird aus einer Superklasse überschrieben
			public void run(String... args) throws Exception {
				System.out.println("Hello World!");
			}
		};
		return action;
	}

}
