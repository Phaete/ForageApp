package com.phaete.backend.forage.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Service class for uploading images to Cloudinary.
 */
@Service
public class CloudinaryService {

	private final Cloudinary cloudinary;

	public CloudinaryService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	/**
	 * Uploads the given image to Cloudinary and returns its URL.
	 *
	 * @param image The image to upload.
	 * @return The URL of the uploaded image.
	 * @throws IOException If the upload fails.
	 */
	public String uploadImage(MultipartFile image) throws IOException {
		File fileToUpload = File.createTempFile("file", null, new File("."));
		image.transferTo(fileToUpload);
		Map response = cloudinary.uploader().upload(fileToUpload, Map.of());
		return response.get("url").toString();
	}
}
