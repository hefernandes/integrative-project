package com.meli.bootcamp.integrativeproject.service;
import org.apache.commons.lang3.RandomStringUtils;
import com.meli.bootcamp.integrativeproject.dto.request.DeliveryRequestDTO;
import com.meli.bootcamp.integrativeproject.dto.response.DeliveryResponseDto;
import com.meli.bootcamp.integrativeproject.entity.Buyer;
import com.meli.bootcamp.integrativeproject.entity.Cart;
import com.meli.bootcamp.integrativeproject.entity.Delivery;
import com.meli.bootcamp.integrativeproject.exception.BusinessException;
import com.meli.bootcamp.integrativeproject.exception.NotFoundException;
import com.meli.bootcamp.integrativeproject.repositories.BuyerRepository;
import com.meli.bootcamp.integrativeproject.repositories.CartRepository;
import com.meli.bootcamp.integrativeproject.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    BuyerRepository buyerRepository;
    @Autowired
    CartRepository cartRepository;

    public DeliveryService() {
    }

    public List<Delivery> findById(String name) {
        return this.deliveryRepository.find(name);
    }

    public DeliveryResponseDto saveDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        Cart cart = (Cart)this.cartRepository.findById(deliveryRequestDTO.getCart_id()).orElseThrow(() -> {
            return new NotFoundException("cart not found");
        });
        Buyer buyer = (Buyer)this.buyerRepository.findById(deliveryRequestDTO.getBuyer_id()).orElseThrow(() -> {
            return new NotFoundException("Buyer not found");
        });
        List<Delivery> deliveryList = this.deliveryRepository.findany(buyer.getId(), cart.getId());
        if (deliveryList.size() == 1) {
            throw new BusinessException("Cart Alredy Delivery".toUpperCase());
        } else {
            Delivery delivery = new Delivery();
            Double frete = 3.5D * deliveryRequestDTO.getWeight();
            delivery.setTrackingCode(RandomStringUtils.randomAlphanumeric(15));
            delivery.setDeadline(LocalDate.now().plusDays(10L));
            delivery.setStatus("Em andamento");
            delivery.setFreight(frete);
            delivery.setCart(cart);
            delivery.setBuyer(buyer);
            delivery.setAddress(deliveryRequestDTO.getAddress());
            delivery.setAddressNumber(deliveryRequestDTO.getAddressNumber());
            delivery.setWeight(deliveryRequestDTO.getWeight());
            this.deliveryRepository.save(delivery);
            return DeliveryResponseDto.builder().nameBuyer(buyer.getName()).status_delivery(delivery.getStatus()).cart_id(cart.getId()).
                    trackingCode(delivery.getTrackingCode()).build();
        }
    }

    public void deleteDelivery(Long id) {
        this.deliveryRepository.deleteById(id);

    }

}