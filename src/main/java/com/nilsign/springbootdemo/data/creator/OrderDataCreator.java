package com.nilsign.springbootdemo.data.creator;

import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.delivery.entity.DeliveryEntity;
import com.nilsign.springbootdemo.domain.order.entity.OrderEntity;
import com.nilsign.springbootdemo.domain.product.entity.ProductEntity;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.order.service.OrderEntityService;
import com.nilsign.springbootdemo.domain.product.service.ProductEntityService;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public final class OrderDataCreator {

  @Autowired
  private OrderEntityService orderEntityService;

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private ProductEntityService productEntityService;

  public void createOrder(
      @NotNull UserEntity userEntity,
      @NotNull @NotEmpty List<ProductEntity> productEntities,
      @NotNull AddressEntity invoiceAddress,
      @NotNull @NotEmpty List<DeliveryEntity> deliveryEntities) {
    Optional<OrderEntity> orderEntity = orderEntityService.save(OrderEntity.builder()
        .user(userEntity)
        .products(new HashSet<>(productEntities))
        .deliveries(new ArrayList<>())
        .invoiceAddress(invoiceAddress)
        .build());
    orderEntity.ifPresent(order -> {
      deliveryEntities.forEach(delivery -> delivery.setOrder(orderEntity.get()));
      order.setDeliveries(deliveryEntities);
      orderEntityService.save(orderEntity.get());
    });
  }

  public void createOrder(
      @NotNull @NotBlank @Email String email,
      @NotNull @NotEmpty Set<Integer> productNumbers,
      @NotNull @Positive @Min(1) Integer numberOfDeliveries) {
    List<ProductEntity> productEntities = new ArrayList<>();
    productNumbers.forEach(productNumber -> {
      Optional<ProductEntity> productEntity
          = productEntityService.findByProductNumber(productNumber);
      productEntity.ifPresent(productEntities::add);
    });
    userEntityService.findByEmail(email).ifPresent(userEntity -> {
      if (userEntity.getCustomer() != null) {
        AddressEntity addressEntity = userEntity.getCustomer().getPostalAddress();
        List<DeliveryEntity> deliveryEntities = new ArrayList<>();
        IntStream.range(1, numberOfDeliveries).forEach(i ->
            deliveryEntities.add(DeliveryEntity.builder().deliveryAddress(addressEntity).build()));
        createOrder(userEntity, productEntities, addressEntity, deliveryEntities);
      }
    });
  }
}
