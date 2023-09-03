package sb.animalsos.web.form.validation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;
    @NotBlank
    private String itemName;
    @NotBlank
    private String loc;
    @NotBlank
    private String content;
}
