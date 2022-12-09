package de.tuhrig.neo4j.domain.location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();

    String save(String street, String houseNumber, String city);
}
