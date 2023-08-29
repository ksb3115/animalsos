package sb.animalsos.domain.item;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {
    //여러개의 store가 동시 접근될수있을땐 HashMap이 아닌 ConcurrentHashMap
    private static final Map<Long,Item> store = new ConcurrentHashMap<>();
    //동시접근하면 값이 꼬일수있으므로 long 대신 AtomicLong
    private static AtomicLong sequence = new AtomicLong();

    public Item save(Item item){
        item.setId(sequence.incrementAndGet());
        store.put(item.getId(),item);
        return item;

    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        //값이 안전하게 ArrayList로 한번 더 감싸기
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
       Item findItem = findById(itemId);
       findItem.setItemName(updateParam.getItemName());
       findItem.setContent(updateParam.getContent());
       findItem.setLocation(updateParam.getLocation());
    }

    public void clearStore(){
        store.clear();
    }
}
