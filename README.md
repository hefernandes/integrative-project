#   integrative-project

## Projeto
O projeto consiste em um e-commerce do Meli com o adicional de produtos CONGELADOS, REFRIGERADOS e FRESCOS.

## Endpoint
Existem diversos endpoints nesta aplicação, desde cadastramento de produtos até a realização do carrinho de compras. O endpoint aqui abordado é o de paginação.
Este endpoint consiste em trazer todos os produtos cadastrados em uma página, podendo ser ordenados por nome, data de validade e temperatura devido o contexto da API.

## Controllers

```JAVA
 @GetMapping({"/{id}"})
    public ResponseEntity<List<Delivery>> findByName(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(this.deliveryService.findById(id));
    }
    @PostMapping({"/Post"})
    public ResponseEntity<DeliveryResponseDto> saves(@Valid @RequestBody DeliveryRequestDTO deliveryDTO) {
        return ResponseEntity.ok().body(this.deliveryService.saveDelivery(deliveryDTO));
    }
```
## Service
### GET
```JAVA
public List<Delivery> findById(Long id) {
        return this.deliveryRepository.find(id);
    }
```
### POST
```JAVA
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
           Double total = cartRepository.getByTotalpriceAndId(cart.getId());
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
            delivery.setTotal_price(total+frete);
            this.deliveryRepository.save(delivery);
            return DeliveryResponseDto.builder().nameBuyer(buyer.getName()).status_delivery(delivery.getStatus()).cart_id(cart.getId()).
                    trackingCode(delivery.getTrackingCode()).build();

        }
        ```
        }
