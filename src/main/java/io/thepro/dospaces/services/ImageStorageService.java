package io.thepro.dospaces.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.thepro.dospaces.entities.Image;

public interface ImageStorageService {

	void saveFile(MultipartFile multipartFile) throws IOException;

	void deleteFile(Long id) throws Exception;
	
	List<Image> getImage();
}
