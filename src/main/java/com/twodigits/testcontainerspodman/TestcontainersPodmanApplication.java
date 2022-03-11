package com.twodigits.testcontainerspodman;

import antlr.ASTNULLType;
import com.twodigits.testcontainerspodman.characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestcontainersPodmanApplication {

	@Autowired
	private CharacterRepository repository;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TestcontainersPodmanApplication.class, args);
		TestcontainersPodmanApplication app = context.getBean(TestcontainersPodmanApplication.class);
		app.run();
	}

	private void run() {
		System.out.println(repository.findAll());
	}

}
