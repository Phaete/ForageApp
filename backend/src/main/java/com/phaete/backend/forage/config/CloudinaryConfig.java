package com.phaete.backend.forage.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the Cloudinary service.
 * It provides a bean for the Cloudinary service.
 * <p>
 * @author -St4n aka Phaete
 */
@Configuration
public class CloudinaryConfig {

	/**
	 * Returns a Cloudinary instance.
	 * <p>
	 * It uses the {@link System#getenv(String)} method to retrieve the
	 * API key through environment variables and
	 * construct a new instance of Cloudinary with the given API Key.
	 * <p>
	 * @return a new Cloudinary instance
	 */
	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(
				System.getenv("CLOUDINARY_URL"));
	}
}
