package com.twodigits.testcontainerspodman;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@Testcontainers
class TestcontainersPodmanApplicationTests {

	@Container
	public GenericContainer redis = new GenericContainer(
			DockerImageName.parse("redis:5.0.3-alpine")
	).withExposedPorts(6379);

	@Test
	void test() {
		assertEquals(true, true);
	}

}
