package de.tuhrig.neo4j.domain;

import de.tuhrig.neo4j.ports.LocationController.LocationDto;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();

    Long save(LocationDto dto);
}
