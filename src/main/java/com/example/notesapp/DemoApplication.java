package com.example.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
		for (String line : Files.readAllLines(Path.of(".env"))) {
			int idx = line.indexOf('=');
			if (idx > 0) {
				System.setProperty(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
			}
		}
		SpringApplication.run(DemoApplication.class, args);
	}
}