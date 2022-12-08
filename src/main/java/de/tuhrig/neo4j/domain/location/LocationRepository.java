package de.tuhrig.neo4j.domain.location;

import de.tuhrig.neo4j.ports.LocationController.LocationDto;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();

    Long save(LocationDto dto);
}
