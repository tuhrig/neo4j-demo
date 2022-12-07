package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.Product;
import de.tuhrig.neo4j.domain.ProductSummary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface Neo4JProductRepository extends Neo4jRepository<Product, String> {
    // Neo4J generated proxy

    Optional<ProductSummary> findBySku(String sku);
}
