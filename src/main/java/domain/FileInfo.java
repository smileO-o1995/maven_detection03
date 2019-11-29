package domain;

/**
 * @author wen
 * @date 2019/11/25 0025-16:59
 */
public class FileInfo {
    private String fileState;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileState(String fileState) {
        this.fileState = fileState;
    }

    public String getFileState() {
        return fileState;
    }
}
