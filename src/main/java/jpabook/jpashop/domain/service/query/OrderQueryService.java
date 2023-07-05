package jpabook.jpashop.domain.service.query;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> orderListV3() {
        List<Order> orderList = orderRepository.findAllItem();

        List<OrderDto> collect = orderList.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return collect;
    }
}
