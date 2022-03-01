package com.org.jp.service;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * 七牛云服务
 *
 * @author samy
 */
@Service
public class QiniuService {

    @Value("${qiniu.bucket}")
    private String bucketName = "";

    @Value("${qiniu.access-key}")
    private String accessKey = "";

    @Value("${qiniu.secret-key}")
    private String secretKey = "";

    private Auth auth = null;

    private Configuration cfg = null;

    private UploadManager uploadManager = null;

    private BucketManager manager = null;

    private Configuration getConfiguration() {
        if (this.cfg == null) {
            this.cfg = new Configuration(Region.autoRegion());
        }
        return this.cfg;
    }

    private Auth getAuth() {
        if (this.auth == null) {
            this.auth = Auth.create(accessKey, secretKey);
        }
        return this.auth;
    }

    private BucketManager getBucketManager() {
        if (this.auth == null) getAuth();
        if (this.manager == null) {
            this.manager = new BucketManager(auth, getConfiguration());
        }
        return this.manager;
    }

    public UploadManager getUploadManager() {
        if (this.uploadManager == null) {
            this.uploadManager = new UploadManager(getConfiguration());
        }
        return this.uploadManager;
    }

    public String getUploadToken() {
        if (this.auth == null) getAuth();
        return auth.uploadToken(bucketName);
    }

    public void uploadFile(File file, String fileName) throws Exception {
        if (this.uploadManager == null) getUploadManager();
        if (this.uploadManager != null) {
            this.uploadManager.put(file, fileName, getUploadToken());
        }
    }

    public void uploadFile(InputStream stream, String fileName) throws Exception {
        if (this.uploadManager == null) getUploadManager();
        if (this.uploadManager != null) {
            this.uploadManager.put(stream, fileName, getUploadToken(), null, null);
        }
    }

    public void uploadFile(byte[] bytes, String fileName) throws Exception {
        if (this.uploadManager == null) getUploadManager();
        if (this.uploadManager != null) {
            this.uploadManager.put(bytes, fileName, getUploadToken());
        }
    }

    public void deleteFile(String fileKey) throws QiniuException {
        getBucketManager();
        this.manager.delete(bucketName, fileKey);
    }

}
