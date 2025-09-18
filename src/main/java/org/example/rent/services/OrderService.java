package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.OrderDTO;
import org.example.rent.entity.Order;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.OrderRepository;
import org.example.rent.services.mappers.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final Logger log = CustomLogger.getLog();

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    //getById(Long id)
    public OrderDTO getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id: " + id + " not found"));
        log.info("Get order with id: " + id);
        return orderMapper.toDtoWithRelations(order);
    }

    //getAll()
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        log.info("Get all orders: " + orders);
        return orders.stream().map(orderMapper::toDtoWithRelations).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(OrderDTO orderDTO) {
        Order order = orderMapper.toEntityWithRelations(orderDTO);
        orderRepository.save(order);
        log.info("Save order with id: " + order.getId());
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id))
            throw new NotFoundException("Order with id: " + id + " not found");
        orderRepository.deleteById(id);
        log.info("Delete order with id: " + id);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, OrderDTO orderDTO) {
        if (!orderRepository.existsById(id))
            throw new NotFoundException("Order with id: " + id + " not found");
        Order order = orderMapper.toEntityWithRelations(orderDTO);
        order.setId(id);
        orderRepository.save(order);
        log.info("Update order with id: " + id);
    }
}
