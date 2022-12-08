package de.tuhrig.neo4j.domain.product;

import de.tuhrig.neo4j.ports.ProductController;

import java.util.Optional;

public interface ProductRepository {
    void save(Product product);

    void save(ProductController.ProductDto dto);

    Optional<ProductSummary> find(String sku);
}
