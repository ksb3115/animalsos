package sb.animalsos.domain.item;

import lombok.Data;

@Data
public class Item {

    private Long id;

    private String itemName;

    private String loc;

    private String content;

    public Item(){

    }

    public Item(String itemName, String loc, String content) {
        this.itemName = itemName;
        this.loc = loc;
        this.content = content;
    }


}
