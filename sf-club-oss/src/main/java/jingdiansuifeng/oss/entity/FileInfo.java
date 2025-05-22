package jingdiansuifeng.oss.entity;

/**
 * 文件类
 */
public class FileInfo {

    private String fileName;


    private String etag;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
