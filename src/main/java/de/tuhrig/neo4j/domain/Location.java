package de.tuhrig.neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor // Empty constructor required as of Neo4j API 2.0.5
@Node("location")
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String houseNumber;
    private String city;

    public Location(
            String street,
            String houseNumber,
            String city
    ) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }
}
