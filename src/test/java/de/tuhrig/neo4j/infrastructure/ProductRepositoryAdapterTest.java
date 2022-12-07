package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.Product;
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
        var dellLaptop = new Product("10001", "Dell Laptop", "Brand new Dell Laptop!");
        productRepository.save(dellLaptop);

        var result = productRepository.find("10001");

        assertThat(result.isPresent()).isTrue();
    }
}

