package sb.animalsos.web.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sb.animalsos.domain.item.Item;
import sb.animalsos.domain.item.ItemRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId ,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item",new Item());
        return "form/addForm";
    }
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam String loc,
                       @RequestParam String content,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setLoc(loc);
        item.setContent(content);

        itemRepository.save(item);

        //model.addAttribute("item",item); //ModelAttribute는 객체를 만들어주고 model에 넣어주는 역할까지 2가지 다 수행!자동 추가,생략가능

        return "form/item";
    }
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item , RedirectAttributes redirectAttributes){

        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item",item); //ModelAttribute는 객체를 만들어주고 model에 넣어주는 역할까지 2가지 다 수행!자동 추가,생략가능
        //return "form/item";//새로고침시 같은 데이터 쌓이는 오류
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/form/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/form/items/{itemId}";
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("ItemA", "강남","내용1"));
        itemRepository.save(new Item("ItemB", "강북","내용2"));
    }


}
