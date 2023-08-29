package sb.animalsos.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;

    private String itemName;
    private String location;
    private String content;

    public Item(){

    }

    public Item(String itemName, String location, String content) {
        this.itemName = itemName;
        this.location = location;
        this.content = content;
    }


}
