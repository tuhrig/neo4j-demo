package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.location.LocationRepository;
import de.tuhrig.neo4j.domain.shop.Shop;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;

import static org.assertj.core.api.Assertions.assertThat;


@DataNeo4jTest
class ShopRepositoryAdapterTest {

    private static Neo4jContainer<?> neo4jContainer;
    private ShopRepositoryAdapter shopRepository;
    private LocationRepository locationRepository;

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
            @Autowired Neo4jClient neo4jClient,
            @Autowired Neo4jTemplate neo4jTemplate,
            @Autowired Neo4JShopRepository neo4JShopRepository,
            @Autowired Neo4JLocationRepository neo4JLocationRepository
    ) {
        shopRepository = new ShopRepositoryAdapter(neo4jClient, neo4JShopRepository);
        locationRepository = new LocationRepositoryAdapter(neo4jTemplate, neo4JLocationRepository);
    }

    @Test
    void should_append_existing_location() {

        shopRepository.save(new Shop("media_markt", "Media Markt"));
        var id = locationRepository.save("Mainstreet", "4", "Karlsruhe");

        shopRepository.appendLocation("media_markt", id);

        var result = shopRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLocations()).hasSize(1);
        assertThat(result.get(0).getLocations().get(0).getId()).isEqualTo(id);
    }
}