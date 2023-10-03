package sb.animalsos.web.form;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import sb.animalsos.domain.item.Item;
import sb.animalsos.domain.item.ItemRepository;
import sb.animalsos.domain.item.UploadFile;
import sb.animalsos.web.file.FileStore;
import sb.animalsos.web.form.validation.ItemSaveForm;
import sb.animalsos.web.form.validation.ItemUpdateForm;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
@Slf4j
public class FormItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

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

    @PostMapping("/add")
    public String addItemV2(@Validated  @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
       //BindingResult는 무조건 ModelAttribute 객체 다음에와야함 순서주의

        //검증실패시 다시 입력창
        if(bindingResult.hasErrors()){
            log.info("errors={}" ,bindingResult); //bindingResult 결과는 model객체에 자동으로 담겨서 넘겨줌
            return "form/addForm";
        }

        //UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setLoc(form.getLoc());
        item.setContent(form.getContent());
        //item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        //검증 성공 시 로직
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
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult){
        //검증실패시 다시 입력창
        if(bindingResult.hasErrors()){
            log.info("errors={}" ,bindingResult); //bindingResult 결과는 model객체에 자동으로 담겨서 넘겨줌
            return "form/editForm";
        }
        Item itemParam = new Item();
        itemParam.setItemName(form.getItemName());
        itemParam.setLoc(form.getLoc());
        itemParam.setContent(form.getContent());

        itemRepository.update(itemId, itemParam);
        return "redirect:/form/items/{itemId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("ItemA", "강남","내용1"));
        itemRepository.save(new Item("ItemB", "강북","내용2"));
    }


}
