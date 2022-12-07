package de.tuhrig.neo4j.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node("product")
public class Product {

    @Id
    private String sku;

    private String name;

    private String description;

    @Relationship(type = "SOLD_BY")
    private Set<Shop> shops = new HashSet<>();

    @Relationship(type = "COMPATIBLE_WITH")
    private Set<Product> compatibleProducts = new HashSet<>();

    @Relationship(type = "REQUIRES")
    private Set<Product> requiredProducts = new HashSet<>();

    private Product() {
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Product(String sku, String name, String description) {
        this.sku = sku;
        this.name = name;
        this.description = description;
    }

    public void soldBy(Shop shop) {
        shops.add(shop);
    }

    public void requires(Product product) {
        requiredProducts.add(product);
    }

    public void compatibleWith(Product product) {
        compatibleProducts.add(product);
    }
}
