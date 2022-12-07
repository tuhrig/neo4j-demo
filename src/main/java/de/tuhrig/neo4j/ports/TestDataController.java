package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.Location;
import de.tuhrig.neo4j.domain.Product;
import de.tuhrig.neo4j.domain.ProductRepository;
import de.tuhrig.neo4j.domain.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public TestDataController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PutMapping(path = "/init")
    public void init() {

        var mediaMarkt = new Shop("media_markt", "Media Markt");
        var edeka = new Shop("edeka", "EDEKA");
        var atu = new Shop("atu", "ATU");

        atu.locatedAt(new Location("atu-l-1", "Hauptstraße", "1b", "Stuttgart"));
        atu.locatedAt(new Location("atu-l-2", "Nebenweg", "5", "Karlsruhe"));

        edeka.locatedAt(new Location("edeka-l-1", "Hauptstraße", "2b", "Stuttgart"));

        mediaMarkt.locatedAt(new Location("media-l-1", "Gasse", "42", "Berlin"));

        var dellLaptop = new Product("10001", "Dell Laptop", "Brand new Dell Laptop!");
        var usbCabel = new Product("20001", "USB Cabel", "USB Cabel (1 meter)");
        var cleaningSpray = new Product("30001", "Cleaning Spray", "ECO BIO Cleaning Spray");

        dellLaptop.soldBy(mediaMarkt);
        dellLaptop.compatibleWith(usbCabel);

        usbCabel.soldBy(mediaMarkt);
        usbCabel.soldBy(atu);
        usbCabel.requires(dellLaptop);
        usbCabel.compatibleWith(dellLaptop);

        cleaningSpray.soldBy(mediaMarkt);
        cleaningSpray.soldBy(edeka);
        cleaningSpray.soldBy(atu);
        cleaningSpray.compatibleWith(dellLaptop);

        productRepository.save(dellLaptop);
        productRepository.save(usbCabel);
        productRepository.save(cleaningSpray);

        logger.info("Created test data!");
    }
}