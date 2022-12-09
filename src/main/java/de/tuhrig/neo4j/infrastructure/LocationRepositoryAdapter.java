package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.location.Location;
import de.tuhrig.neo4j.domain.location.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * What to see here:
 * <p>
 * - A hexagonal architecture pattern (an adapter between domain interface and concrete infrastructure)
 * - Creation of a new node with returning the generated ID
 */
@Service
@AllArgsConstructor
public class LocationRepositoryAdapter implements LocationRepository {

    private final Neo4JLocationRepository neo4JLocationRepository;

    @Override
    public List<Location> findAll() {
        return neo4JLocationRepository.findAll();
    }

    @Override
    public String save(String street, String houseNumber, String city) {
        var location = new Location(street, houseNumber, city);
        var saved = neo4JLocationRepository.save(location);
        return saved.getId();
    }
}
