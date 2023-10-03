package sb.animalsos.domain.item;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName; //서버에 저장되는 파일이름


    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
