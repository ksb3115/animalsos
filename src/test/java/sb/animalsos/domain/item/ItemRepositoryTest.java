package sb.animalsos.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("item1","soheul","rescue the dog!");
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);

    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("item2","dobonggu","rescue the cat!");
        Item item2 = new Item("item3","jamsil","rescue the bird!");

        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> result =itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);
    }

    @Test
    void updateItem(){
        //given
        Item item = new Item("item","dobonggu","rescue the cat!");
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("item2","sadang","rescue the animal!");
        itemRepository.update(itemId,updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getContent()).isEqualTo(updateParam.getContent());
        assertThat(findItem.getLocation()).isEqualTo(updateParam.getLocation());

    }
}