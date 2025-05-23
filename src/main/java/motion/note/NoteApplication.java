package motion.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv("AWS_ACCESS_KEY_ID"));
		SpringApplication.run(NoteApplication.class, args);
	}

}
