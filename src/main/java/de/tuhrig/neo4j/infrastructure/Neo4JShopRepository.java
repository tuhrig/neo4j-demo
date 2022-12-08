package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.shop.Shop;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
interface Neo4JShopRepository extends Neo4jRepository<Shop, String> {
    // Neo4J generated proxy
}
