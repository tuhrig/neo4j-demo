package de.tuhrig.neo4j.domain.shop;

import java.util.List;

public interface ShopRepository {
    void appendLocation(String shopId, Long locationId);

    List<Shop> findAll();

    void save(Shop shop);
}
