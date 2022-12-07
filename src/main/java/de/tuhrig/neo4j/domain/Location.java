package de.tuhrig.neo4j.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node("location")
public class Location {

    @Id
    private String id;

    private String street;
    private String houseNumber;
    private String city;

    private Location() {
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Location(
            String id,
            String street,
            String houseNumber,
            String city
    ) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }
}
