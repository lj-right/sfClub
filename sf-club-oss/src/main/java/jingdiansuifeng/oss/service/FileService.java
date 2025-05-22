package jingdiansuifeng.oss.service;

import jingdiansuifeng.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileService {

    @Resource
    private final StorageAdapter storageAdapter;

    public FileService(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    public List<String> getAllBucket() {
        return storageAdapter.getAllBucket();
    }

    public String getUrl(String bucketName,String objectName){
        return storageAdapter.getUrl(bucketName,objectName);
    }

    public String uploadFile(MultipartFile uploadFile, String bucket, String objectName){
        storageAdapter.uploadFile(uploadFile,bucket,objectName);
        objectName = objectName + "/" + uploadFile.getOriginalFilename();
        return storageAdapter.getUrl(bucket, objectName);

    }


}
