package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import jpabook.jpashop.domain.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        for(Order order : orderList) {
            order.getMember().getName();  // getMember()까지는 프록시 객체, getName()부터 실제 데이터를 조회
            order.getDelivery().getAddress();
        }

        return orderList;
    }

    @GetMapping("/api/v2/orders")
    public List<SimpleOrderDTO> ordersV2() {
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDTO> result = orderList.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(toList());

        return result;
    }

    @Data
    static class SimpleOrderDTO {
        private Long orderId;
        private String orderName;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order) {
            orderId = order.getId();
            orderName = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

    @GetMapping("/api/v3/orders")
    public List<SimpleOrderDTO> orderV3() {
        List<Order> orderList = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> result = orderList.stream()
                .map(SimpleOrderDTO::new)
                .collect(toList());

        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }
}
