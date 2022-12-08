package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.product.ProductRepository;
import de.tuhrig.neo4j.domain.product.ProductSummary;
import lombok.Data;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Driver driver;
    private final ProductRepository productRepository;

    public ProductController(
            Driver driver,
            ProductRepository productRepository
    ) {
        this.driver = driver;
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/products", produces = APPLICATION_JSON_VALUE)
    public List<String> getProducts() {
        try (Session session = driver.session()) {
            return session
                    .run("MATCH (p:Product) RETURN p ORDER BY p.name ASC")
                    .list(r -> r.get("p").asNode().get("name").asString());
        }
    }

    @GetMapping(path = "/products/{sku}", produces = APPLICATION_JSON_VALUE)
    public Optional<ProductSummary> getProduct(@PathVariable String sku) {
        return productRepository.find(sku);
    }

    @PutMapping(path = "/products")
    public void updateProduct(@RequestBody ProductDto dto) {
        productRepository.save(dto);
        logger.info("Updated product. [sku={}, name={}]", dto.getSku(), dto.getName());
    }

    @Data
    public static class ProductDto {
        private final String sku;
        private final String name;
    }
}