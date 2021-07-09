package io.vallegande.bucket.bucket.controller;


import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.vallegande.bucket.bucket.service.StorageService;



@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;


    @GetMapping("/listBucket")
    public ResponseEntity<List<Bucket>> listBucket() {
        return new ResponseEntity<>(service.listBucket(), HttpStatus.OK);
    }

    @GetMapping("/listObjects")
    public ResponseEntity<ObjectListing> listObject() {
        return new ResponseEntity<>(service.listObject(), HttpStatus.OK);
    }


    @PostMapping("/createDir")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "dir_path") String directory) {
        return new ResponseEntity<>(service.createFolder(directory), HttpStatus.OK);
    }
     // TODO: fix must provide a directory to upload
    @PostMapping("/uploadDir")
    public ResponseEntity<String> uploadFolder(@RequestParam(value = "dir_path") String directory) {
          return new ResponseEntity<>(service.uploadDir(directory), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDirectory(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}