package com.nilsign.springbootdemo.data;

import com.nilsign.springbootdemo.data.creator.OrderDataCreator;
import com.nilsign.springbootdemo.data.creator.ProductDataCreator;
import com.nilsign.springbootdemo.data.creator.RatingDataCreator;
import com.nilsign.springbootdemo.data.creator.UserDataCreator;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Profile("DEV")
@Configuration
public class DevDataCreator {

  private static final String ADMIN_EMAIL = "ada.mistrate@nilsign.com";
  private static final String SELLER_EMAIL = "selma.sellington@nilsign.com";
  private static final String BUYER_1_EMAIL = "bud.buyman@nilsign.com";
  private static final String BUYER_2_EMAIL = "mad.allistoles@nilsign.com";

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
    if (userEntityService.findByEmail(ADMIN_EMAIL).isPresent()
        && userEntityService.findByEmail(SELLER_EMAIL).isPresent()
        && userEntityService.findByEmail(BUYER_1_EMAIL).isPresent()
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
    userDataCreator.createAdminSellerUserIfNotExists(
        "Ada",
        "Mistrate",
        ADMIN_EMAIL);
    userDataCreator.createSellerUserIfNotExists(
        "Selma",
        "Sellington",
        SELLER_EMAIL);
    userDataCreator.createBuyerUserIfNotExist(
        "Bud",
        "Buyman",
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
