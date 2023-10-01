package sb.animalsos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sb.animalsos.domain.item.Item;
import sb.animalsos.domain.item.ItemRepository;
import sb.animalsos.domain.member.Member;
import sb.animalsos.domain.member.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("강아지 구조해주세요", "도봉구", "유기견강아지를 구조해주세요"));
        itemRepository.save(new Item("고양이가 차에 치였어요", "서대문구", "고양이가 교통사고를 당했어요 구조해주세요"));
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test");
        member.setName("tester1");
        memberRepository.save(member);
    }
}