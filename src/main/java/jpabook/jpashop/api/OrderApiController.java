package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orderList")
    public List<Order> orderListV1() {
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orderList) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }

        return orderList;
    }

    @GetMapping("/api/v2/orderList")
    public List<OrderDTO> orderListV2() {
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO> collect = orderList.stream()
                .map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/orderList")
    public List<OrderDTO> orderListV3() {
        List<Order> orderList = orderRepository.findAllItem();

        List<OrderDTO> collect = orderList.stream()
                .map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        return collect;
    }

    @Getter
    static class OrderDTO {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDTO> orderItems;

        public OrderDTO(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDTO(orderItem))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDTO {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDTO(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
