package de.tuhrig.neo4j.infrastructure;

import de.tuhrig.neo4j.domain.Location;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends Neo4jRepository<Location, String> {
    //
}
