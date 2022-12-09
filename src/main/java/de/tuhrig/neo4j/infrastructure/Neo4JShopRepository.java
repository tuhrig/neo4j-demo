package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.shop.Shop;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * What to see here:
 * <p>
 * - A Neo4J repository which will be implemented automatically by Spring Data
 */
@Repository
interface Neo4JShopRepository extends Neo4jRepository<Shop, String> {
    // Neo4J generated proxy
}
