package io.vallegande.bucket.bucket.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticbeanstalk.model.EventDescription;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class StorageService {
    static final String SUFFIX = "/";
    static final boolean recursive = false;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public List<Bucket> listBucket() {
        List<Bucket> buckets = s3Client.listBuckets();
        return buckets;
    }

    public ObjectListing listObject() {
        ObjectListing objectListing = s3Client.listObjects(bucketName);

        return objectListing;
    }

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }

    public String createFolder(String dir_path) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, dir_path + SUFFIX, emptyContent, metadata);

        // send request to S3 to create folder
        s3Client.putObject(putObjectRequest);
        return "create folder";
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {

        }
        return convertedFile;
    }

    public String uploadDir(String dir_path) {

        // snippet-start:[s3.java1.s3_xfer_mgr_upload.directory]
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        File directory = new File("/home/opc/store");
        try {
            List<File> fileList = Arrays.asList(directory.listFiles());
         Transfer transfer = transferManager.uploadFileList(bucketName, "t/", directory, fileList);
         waitForCompletion(transfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        transferManager.shutdownNow();
        return "Upload Direcotry";
    }

    public static void waitForCompletion(Transfer xfer) {
        try {
            xfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println("Amazon service error");
            System.exit(1);
        } catch (AmazonClientException e) {
            System.err.println("Amazon client error");
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Transfer interrupted");
            System.exit(1);
        }
    }

}
