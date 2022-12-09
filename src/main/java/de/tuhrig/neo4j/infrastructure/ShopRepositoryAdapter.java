package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.shop.Shop;
import de.tuhrig.neo4j.domain.shop.ShopRepository;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * What to see here:
 * <p>
 * - A hexagonal architecture pattern (an adapter between domain interface and concrete infrastructure)
 * - A Neo4J query to create a single relation between two existing nodes
 */
@Service
public class ShopRepositoryAdapter implements ShopRepository {

    private Neo4jClient neo4jClient;
    private Neo4JShopRepository neo4JShopRepository;

    public ShopRepositoryAdapter(
            Neo4jClient neo4jClient,
            Neo4JShopRepository neo4JShopRepository
    ) {
        this.neo4jClient = neo4jClient;
        this.neo4JShopRepository = neo4JShopRepository;
    }

    @Override
    public void appendLocation(String shopId, String locationId) {
        var query = """
                MATCH (s:shop)
                MATCH (l:location)
                WHERE s.id = '%s' AND l.id = '%s'
                MERGE (s)-[:LOCATED_AT]->(l)
                RETURN *
                """.formatted(shopId, locationId);
        neo4jClient.query(query).run();
    }

    @Override
    public List<Shop> findAll() {
        return neo4JShopRepository.findAll();
    }

    @Override
    public void save(Shop shop) {
        neo4JShopRepository.save(shop);
    }
}
