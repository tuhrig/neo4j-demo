package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.location.Location;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Neo4JLocationRepository extends Neo4jRepository<Location, String> {
    // Neo4J generated proxy
}
