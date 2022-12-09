package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.product.Product;
import de.tuhrig.neo4j.domain.product.ProductRepository;
import de.tuhrig.neo4j.domain.product.ProductSummary;
import de.tuhrig.neo4j.ports.ProductController;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * What to see here:
 * <p>
 * - A hexagonal architecture pattern (an adapter between domain interface and concrete infrastructure)
 * - A READ using a projection to only read a subset of the data (not the complete graph)
 * - A WRITE using a projection to only write a subset of the data (not the complete graph)
 */
@Service
public class ProductRepositoryAdapter implements ProductRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4JProductRepository neo4JProductRepository;

    public ProductRepositoryAdapter(
            Neo4jTemplate neo4jTemplate,
            Neo4JProductRepository neo4JProductRepository
    ) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4JProductRepository = neo4JProductRepository;
    }


    @Override
    public void save(Product product) {
        neo4JProductRepository.save(product);
    }

    @Override
    public void save(ProductController.ProductDto dto) {
        neo4jTemplate.save(Product.class).one(dto);
    }

    @Override
    public Optional<ProductSummary> find(String sku) {
        return neo4JProductRepository.findBySku(sku);
    }

    public Optional<Product> findBySku(String sku) {
        return neo4JProductRepository.findById(sku);
    }
}
