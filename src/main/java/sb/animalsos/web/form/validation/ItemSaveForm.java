package sb.animalsos.web.form.validation;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemSaveForm {

    @NotBlank
    private String itemName;
    @NotBlank
    private String loc;
    @NotBlank
    private String content;
}
