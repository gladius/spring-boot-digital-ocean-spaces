package io.thepro.dospaces.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.thepro.dospaces.entities.Image;
import io.thepro.dospaces.repositories.ImageRepository;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

	@Value("${do.space.bucket}")
	private String doSpaceBucket;

	String FOLDER = "files/";

	@Autowired
	AmazonS3 s3Client;

	@Autowired
	ImageRepository imageRepo;

	@Override
	public void saveFile(MultipartFile multipartFile) throws IOException {
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		String imgName = FilenameUtils.removeExtension(multipartFile.getOriginalFilename());
		String key = FOLDER + imgName + "." + extension;
		saveImageToServer(multipartFile, key);
		Image image = new Image();
		image.setName(imgName);
		image.setExt(extension);
		image.setCreatedtime(new Timestamp(new Date().getTime()));
		imageRepo.save(image);
	}

	@Override
	public void deleteFile(Long fileId) throws Exception {
		Optional<Image> imageOpt = imageRepo.findById(fileId);
		if (imageOpt.get() != null) {
			Image image = imageOpt.get();
			String key = FOLDER + image.getName() + "." + image.getExt();
			s3Client.deleteObject(new DeleteObjectRequest(doSpaceBucket, key));
			imageRepo.delete(image);
		}
	}

	private void saveImageToServer(MultipartFile multipartFile, String key) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getInputStream().available());
		if (multipartFile.getContentType() != null && !"".equals(multipartFile.getContentType())) {
			metadata.setContentType(multipartFile.getContentType());
		}
		s3Client.putObject(new PutObjectRequest(doSpaceBucket, key, multipartFile.getInputStream(), metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	@Override
	public List<Image> getImage() {
		return (List<Image>) imageRepo.findAll();
	}

}
