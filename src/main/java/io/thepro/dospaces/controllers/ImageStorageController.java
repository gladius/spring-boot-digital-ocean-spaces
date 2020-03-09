package io.thepro.dospaces.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.thepro.dospaces.services.FileStorageService;

@RestController
public class FileStorageController {

	@Autowired
	FileStorageService service;

	@PostMapping("/{fileName}")
	public void saveImage(@RequestParam(value = "image", required = true) MultipartFile image,
			@PathVariable("fileName") String imgName) throws IOException {
		service.saveFile(image, imgName);
	}

	@DeleteMapping("/{name}")
	public void deleteById(@PathVariable("fileName") String fileName) throws Exception {
		service.deleteFile(fileName);
	}

}
