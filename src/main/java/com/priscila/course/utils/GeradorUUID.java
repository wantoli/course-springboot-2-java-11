package com.priscila.course.utils;

import java.util.Random;
import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class GeradorUUID {

	public static UUID getUUIDUnico(String seed) {
		return Generators.randomBasedGenerator(new Random(Long.parseLong(seed))).generate();
	}
}
