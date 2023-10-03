package sb.animalsos.web.form.validation;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ItemSaveForm {

    @NotBlank
    private String itemName;
    @NotBlank
    private String loc;
    @NotBlank
    private String content;

    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
