package system.system_cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemCinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemCinemaApplication.class, args);
        System.out.println("System cinema application started at port 8080 ...............");
    }

}
