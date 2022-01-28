package com.atpl.mmg.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.exception.MmgRestException.FILE_UPLOAD_FAILED;

@Component
public class AwsFileUtils {
	
	@Autowired
	MMGProperties mmgProperties;
	
	public void uploadFile(String fileName, MultipartFile file) throws IOException {
		try {
			AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
					mmgProperties.getSecretKey());
			@SuppressWarnings("deprecation")
			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
			String bucketName = mmgProperties.getMasterBucket();
			ObjectMetadata md = new ObjectMetadata();
			md.setContentLength(file.getSize());
			md.setContentType(file.getContentType());

			InputStream is = file.getInputStream();
			s3client.putObject(new PutObjectRequest(bucketName, fileName, is, md));
		} catch (Exception e) {
			throw new FILE_UPLOAD_FAILED();
		}

	}

}
