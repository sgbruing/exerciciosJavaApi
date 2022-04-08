package br.com.letscode.santander;

import br.com.letscode.santander.model.BDClientes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantanderApplication {
	public static BDClientes bdClientes = new BDClientes();
	public static void main(String[] args) {
		SpringApplication.run(SantanderApplication.class, args);
	}

}
