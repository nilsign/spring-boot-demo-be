package com.nilsign.springbootdemo.data;

import com.nilsign.springbootdemo.data.creator.OrderDataCreator;
import com.nilsign.springbootdemo.data.creator.ProductDataCreator;
import com.nilsign.springbootdemo.data.creator.RatingDataCreator;
import com.nilsign.springbootdemo.data.creator.UserDataCreator;
import com.nilsign.springbootdemo.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Profile("DEV")
@Configuration
public class DevDataCreator {

  // TODO(nilsheumer): Ada Mistrate is missing here, but there is an according Keycloak user with
  // the ADMIN client role.
  // TODO(nilsheumer): This should be a seller. Ensure there is also an according Keycloak user and
  // do not forget to update the Keycloak Docker image, after updating the container.
  private static final String BUYER_2_EMAIL = "mad.alistoles@gmail.com";
  private static final String BUYER_1_EMAIL = "bud.buyman@gmail.com";

  private static final Integer PRODUCT_1_NUMBER = 1;
  private static final Integer PRODUCT_2_NUMBER = 2;
  private static final Integer PRODUCT_3_NUMBER = 3;

  @Autowired
  private UserDataCreator userDataCreator;

  @Autowired
  private ProductDataCreator productDataCreator;

  @Autowired
  private RatingDataCreator ratingDataCreator;

  @Autowired
  private OrderDataCreator orderDataCreator;

  @Autowired
  private UserEntityService userEntityService;

  @Transactional
  public void createDevDataIfNotExist() {
    if (userEntityService.findByEmail(BUYER_1_EMAIL).isPresent()
        && userEntityService.findByEmail(BUYER_2_EMAIL).isPresent()) {
      log.info("DEV data already exists - skip DEV data creation.");
      return;
    }
    log.info("Creating DEV data...");
    createUsers();
    createProducts();
    createRatings();
    createOrders();
    log.info("DEV data creation done.");
  }

  private void createUsers() {
    userDataCreator.createBuyerUserIfNotExist(
        "Bud",
        "Buymann",
        BUYER_1_EMAIL,
        "Buttmens Street 13",
        "48308",
        "Buttington",
        "USA");
    userDataCreator.createBuyerUserIfNotExist(
        "Mad",
        "Alistoles",
        BUYER_2_EMAIL,
        "Notenough Way 66",
        "69693",
        "Greedcreek",
        "USA");
  }

  private void createProducts() {
    productDataCreator.createProductIfNotExist(
        PRODUCT_1_NUMBER, "Book ", BigDecimal.valueOf(19.99));
    productDataCreator.createProductIfNotExist(
        PRODUCT_2_NUMBER, "Board Game", BigDecimal.valueOf(79.90));
    productDataCreator.createProductIfNotExist(
        PRODUCT_3_NUMBER, "Video Game", BigDecimal.valueOf(39.99));
  }

  private void createRatings() {
    ratingDataCreator.createRatingIfNotExist(
        BUYER_1_EMAIL, PRODUCT_2_NUMBER, 4.5f, "Good Product!");
    ratingDataCreator.createRatingIfNotExist(
        BUYER_1_EMAIL, PRODUCT_3_NUMBER, 2.5f);
    ratingDataCreator.createRatingIfNotExist(
        BUYER_2_EMAIL, PRODUCT_3_NUMBER, 1.5f, "Really bad quality.");
  }

  private void createOrders() {
    orderDataCreator.createOrder(
        BUYER_1_EMAIL,
        Set.of(PRODUCT_1_NUMBER, PRODUCT_2_NUMBER, PRODUCT_3_NUMBER),
        3);
    orderDataCreator.createOrder(
        BUYER_2_EMAIL,
        Set.of(PRODUCT_3_NUMBER),
        1);
  }
}
