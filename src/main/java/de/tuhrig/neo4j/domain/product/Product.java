package de.tuhrig.neo4j.domain.product;

import de.tuhrig.neo4j.domain.shop.Shop;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

/**
 * What to see here:
 * <p>
 * - A complex node entity
 * - Multiple relations to different kind of other nodes
 * - A business key used as the ID (no generated ID)
 * - Heavy use of Lombok annotations
 * - Meaningful (domain) methods to interact with the object (soldBy, requires, compatibleWith)
 */
@Node("product")
@Data
@Builder
@NoArgsConstructor // For the Neo4j API
@AllArgsConstructor // For the builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Just the business key as the identity!
public class Product {

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    @Id
    @EqualsAndHashCode.Include
    private String sku;

    private String name;

    private String description;

    @Singular
    @Relationship(type = "SOLD_BY")
    private Set<Shop> shops;

    @Singular
    @Relationship(type = "COMPATIBLE_WITH")
    private Set<Product> compatibleProducts;

    @Singular
    @Relationship(type = "REQUIRES")
    private Set<Product> requiredProducts;

    public void soldBy(Shop shop) {
        shops.add(shop);
        logger.info("Product is sold by another shop now! [sku={}, shop={}]", sku, shop.getId());
    }

    public void requires(Product product) {
        requiredProducts.add(product);
        logger.info("Product requires another product! [sku={}, product={}]", sku, product.sku);
    }

    public void compatibleWith(Product product) {
        compatibleProducts.add(product);
        logger.info("Product is compatible with another product! [sku={}, product={}]", sku, product.sku);
    }
}
