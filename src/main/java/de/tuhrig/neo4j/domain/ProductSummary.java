package de.tuhrig.neo4j.domain;

import java.util.Set;

public interface ProductSummary {

    String getSku();

    String getName();

    String getDescription();

    Set<ShopId> getShops();

    Set<ProductId> getCompatibleProducts();

    Set<ProductId> getRequiredProducts();

    interface ShopId {
        String getId();
    }

    interface ProductId {
        String getSku();
    }
}
