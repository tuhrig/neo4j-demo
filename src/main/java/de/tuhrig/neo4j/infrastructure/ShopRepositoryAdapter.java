package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.shop.Shop;
import de.tuhrig.neo4j.domain.shop.ShopRepository;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void appendLocation(String shopId, Long locationId) {
        var query = """
                MATCH (s:shop)
                MATCH (l:location)
                WHERE s.id = '%s' AND ID(l) = %s
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
