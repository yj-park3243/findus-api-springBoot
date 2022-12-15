package com.findus.findus.common.file;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomFileUtil {

	public static String fileFullPath;

	@Value("${mode}")
	public String mode; //파일 구분자
	@Value("${common.filePath}")
	public String UPLOAD_PATH; //파일 구분자

	@Value("${cloud.aws.s3.bucket}")
	public String bucketName;

	@Value("${cloud.aws.credentials.accessKey}")
	public String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	public String secretKey;

	@Value("${cloud.aws.region.static}")
	public String region;

	/**
	 * 파일 업로드.
	 */
	public FileVO saveSingleFile(String category, MultipartFile upfiles) {
		String filePath = UPLOAD_PATH;

		FileVO filedo = new FileVO();
		if (!upfiles.isEmpty()) {
			String newName = getNewName();

			String org_name = upfiles.getOriginalFilename();
			String pjt = "";
			try {
				pjt = org_name.substring(org_name.lastIndexOf('.'), org_name.length()); // 확장자
			} catch (Exception e) {
				pjt = "";
			}
			fileFullPath = uploadFileV1( category + "/" + newName + pjt, upfiles); //S3 업로드
			//filedo.setFilename( fileFullPath );
			filedo.setFilename( UPLOAD_PATH + "/" + category + "/" + newName + pjt);

			filedo.setRealname(upfiles.getOriginalFilename());
			filedo.setFilesize(upfiles.getSize());
			filedo.setFilesizeString(getFilesizeString(upfiles.getSize()));
			filedo.setFilepath(filePath + "/");
		} else {
			filedo.setFilename(null);
			filedo.setRealname(null);
			filedo.setFilesize(0);
			filedo.setFilepath(null);
		}
		return filedo;
	}

	/**
	 * 날짜로 새로운 파일명 부여.
	 */
	public String getNewName() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return ft.format(new Date()) + (int) (Math.random() * 10);
	}

	public static String getFilesizeString(long filesize) {
		return getFilesizeString(filesize, "#,###.00 ");
	}

	public static String getFilesizeString(long filesize, String format) {
		double size = filesize;
		String tail = "byte";
		if (1024L <= filesize)
			if (1048576L > filesize) {
				size /= 1024D;
				tail = "KB";
			} else if (1073741824L > filesize) {
				size /= 1048576D;
				tail = "MB";
			} else if (1099511627776L > filesize) {
				size /= 1073741824D;
				tail = "GB";
			} else {
				size /= 1099511627776D;
				tail = "TB";
			}
		return (new StringBuilder(String.valueOf((new DecimalFormat(format)).format(size)))).append(tail).toString();
	}

	// S3 fileUpload
	public String uploadFileV1(String uploadFileName, MultipartFile multipartFile) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(bucketName, uploadFileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			try {
				throw new Exception();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return amazonS3Client.getUrl(bucketName, uploadFileName).toString();
	}
}
