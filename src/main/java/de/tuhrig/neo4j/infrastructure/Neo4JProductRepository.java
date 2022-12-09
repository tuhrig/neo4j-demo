package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.product.Product;
import de.tuhrig.neo4j.domain.product.ProductSummary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * What to see here:
 * <p>
 * - A Neo4J repository which will be implemented automatically by Spring Data
 */
@Repository
interface Neo4JProductRepository extends Neo4jRepository<Product, String> {
    // Neo4J generated proxy

    Optional<ProductSummary> findBySku(String sku);
}
