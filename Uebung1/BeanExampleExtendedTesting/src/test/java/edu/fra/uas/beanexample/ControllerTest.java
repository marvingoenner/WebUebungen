package edu.fra.uas.beanexample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.fra.uas.controller.BeanController;
import edu.fra.uas.service.MessageService;

//Annotation legt fest, dass JUnit Testläufe gemacht werden
@SpringBootTest
public class ControllerTest {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private BeanController beanController;

    @Test
    void testController() {
        //Simpler Vergleich, gibt Fehler aus, wenn dem nicht so ist und beschreibt den Fehler genauer
        assertThat(beanController.putMessage("Das ist ein Test")).isEqualTo("Das ist ein Test");
    }

    @Test
    void testIncrement(){
        //Prüfung initialen Zählwert
        assertThat(messageService.getCounter()).isEqualTo(0);

        //Erhöhe den Zähler und prüfe den neuen Wert
        messageService.increment();
        assertThat(messageService.getCounter()).isEqualTo(1);
    }

}
