package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.location.Location;
import de.tuhrig.neo4j.domain.product.Product;
import de.tuhrig.neo4j.domain.product.ProductRepository;
import de.tuhrig.neo4j.domain.shop.Shop;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestDataController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductRepository productRepository;

    @PutMapping(path = "/init")
    public void init() {

        var mediaMarkt = new Shop("media_markt", "Media Markt");
        var edeka = new Shop("edeka", "EDEKA");
        var atu = new Shop("atu", "ATU");

        atu.locatedAt(new Location("Hauptstraße", "1b", "Stuttgart"));
        atu.locatedAt(new Location("Nebenweg", "5", "Karlsruhe"));
        edeka.locatedAt(new Location("Hauptstraße", "2b", "Stuttgart"));
        mediaMarkt.locatedAt(new Location("Gasse", "42", "Berlin"));

        var usbCable = Product.builder()
                .sku("20001")
                .name("USB Cabel")
                .description("USB Cabel (1 meter)")
                .shop(mediaMarkt)
                .shop(atu)
                .build();

        var dellLaptop = Product.builder()
                .sku("10001")
                .name("Dell Laptop")
                .description("Brand new Dell Laptop!")
                .shop(mediaMarkt)
                .compatibleProduct(usbCable)
                .build();

        var cleaningSpray = Product.builder()
                .sku("30001")
                .name("Cleaning Spray")
                .description("ECO BIO Cleaning Spray")
                .requiredProduct(dellLaptop)
                .compatibleProduct(dellLaptop)
                .shop(mediaMarkt)
                .shop(edeka)
                .shop(atu)
                .build();

        usbCable.compatibleWith(dellLaptop); // Cyclic dependency between dellLaptop and usbCable

        productRepository.save(dellLaptop);
        productRepository.save(usbCable);
        productRepository.save(cleaningSpray);

        logger.info("Created test data!");
    }
}