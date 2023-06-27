package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        for(Order order : orderList) {
            order.getMember().getName();  // getMember()까지는 프록시 객체, getName()부터 실제 데이터를 조회
            order.getDelivery().getAddress();
        }

        return orderList;
    }
}
