package sb.animalsos.domain.item;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;

    private String itemName;

    private String loc;

    private String content;

    private UploadFile attachFile;

    private List<UploadFile> imageFiles;

    public Item(){

    }

    public Item(String itemName, String loc, String content) {
        this.itemName = itemName;
        this.loc = loc;
        this.content = content;
    }


}
