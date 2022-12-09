package de.tuhrig.neo4j.domain.location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@NoArgsConstructor // Empty constructor required as of Neo4j API 2.0.5
@Node("location")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

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
