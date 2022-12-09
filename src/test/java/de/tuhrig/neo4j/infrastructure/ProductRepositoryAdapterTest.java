package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.product.Product;
import de.tuhrig.neo4j.domain.shop.Shop;
import de.tuhrig.neo4j.ports.ProductController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;

import static org.assertj.core.api.Assertions.assertThat;

@DataNeo4jTest
class ProductRepositoryAdapterTest {

    private static Neo4jContainer<?> neo4jContainer;
    private ProductRepositoryAdapter productRepository;

    @BeforeAll
    static void initializeNeo4j() {
        neo4jContainer = new Neo4jContainer<>("neo4j:4.3.6").withAdminPassword("123456");
        neo4jContainer.start();
    }

    @AfterAll
    static void stopNeo4j() {
        neo4jContainer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @BeforeEach
    void setUp(
            @Autowired Neo4jTemplate neo4jTemplate,
            @Autowired Neo4JProductRepository neo4JProductRepository
    ) {
        productRepository = new ProductRepositoryAdapter(neo4jTemplate, neo4JProductRepository);
    }

    @Test
    void should_save_product() {
        var dellLaptop = Product.builder()
                .sku("10001")
                .name("Dell Laptop")
                .description("Brand new Dell Laptop!")
                .build();
        productRepository.save(dellLaptop);

        var result = productRepository.find("10001");

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void should_only_update_name() {
        var mediaMarkt = new Shop("media_markt", "Media Markt");
        var dellLaptop = Product.builder()
                .sku("10001")
                .name("Dell Laptop")
                .description("Brand new Dell Laptop!")
                .shop(mediaMarkt)
                .build();

        productRepository.save(dellLaptop);

        var before = productRepository.findBySku("10001");
        assertThat(before).isPresent();
        assertThat(before.get().getShops()).hasSize(1);
        assertThat(before.get().getName()).isEqualTo("Dell Laptop");

        productRepository.save(new ProductController.ProductDto("10001", "New Name!"));

        var after = productRepository.findBySku("10001");
        assertThat(after).isPresent();
        assertThat(after.get().getShops()).hasSize(1); // Not updated! Still Media Markt!
        assertThat(after.get().getName()).isEqualTo("New Name!"); // Updated!
    }
}

