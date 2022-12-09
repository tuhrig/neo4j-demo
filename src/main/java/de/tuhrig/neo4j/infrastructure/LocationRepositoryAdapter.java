package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.location.Location;
import de.tuhrig.neo4j.domain.location.LocationRepository;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationRepositoryAdapter implements LocationRepository {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4JLocationRepository neo4JLocationRepository;

    public LocationRepositoryAdapter(
            Neo4jTemplate neo4jTemplate,
            Neo4JLocationRepository neo4JLocationRepository
    ) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4JLocationRepository = neo4JLocationRepository;
    }

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
