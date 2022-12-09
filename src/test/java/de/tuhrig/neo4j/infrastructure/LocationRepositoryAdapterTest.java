package de.tuhrig.neo4j.infrastructure;

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
class LocationRepositoryAdapterTest {

    private static Neo4jContainer<?> neo4jContainer;
    private LocationRepositoryAdapter locationRepository;

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
            @Autowired Neo4JLocationRepository neo4JLocationRepository
    ) {
        locationRepository = new LocationRepositoryAdapter(neo4jTemplate, neo4JLocationRepository);
    }

    @Test
    void should_save_location_from_dto() {
        var id = locationRepository.save("Mainstreet", "42", "Berlin");

        var result = locationRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(id).isNotNull();
    }
}