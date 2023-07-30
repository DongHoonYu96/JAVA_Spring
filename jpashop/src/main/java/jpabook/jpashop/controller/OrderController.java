package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderService;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model){
        //고객정보, 상품정보를 model에 담아 뷰에넘긴다.

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    //"제출" 누르면 /order URL을 POST방식으로 호출
    //고객아이디, 아이템아이디, 수량 받아서 함수내용실행
    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        //컨트롤러에서 memberId 등 엔티티를 찾고 수정해도 되지않나요?
        // 단점 : 트랜잭션이 아니기떄문에 영속성이아님. DB반영이안됨.
        //-> 컨트롤러에서는 넘기기만하고 서비스계층에서 처리하라.
        return "redirect:/orders";
    }
}
