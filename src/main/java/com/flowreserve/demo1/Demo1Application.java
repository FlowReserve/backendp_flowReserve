package com.flowreserve.demo1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Demo1Application {

	public static void main(String[] args) {
		//Carga el fichero .env de la raíz del proyecto y establece las variables de entorno
		if(Files.exists(Paths.get(".env"))){
			Dotenv dotenv = Dotenv.load();

			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}
		SpringApplication.run(Demo1Application.class, args);
	}

}
