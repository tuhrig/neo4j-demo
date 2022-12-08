package de.tuhrig.neo4j.domain.shop;

import de.tuhrig.neo4j.domain.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // Empty constructor required as of Neo4j API 2.0.5
@Node("shop")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shop {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String name;

    @Relationship(type = "LOCATED_AT")
    private List<Location> locations = new ArrayList<>();

    public Shop(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void locatedAt(Location location) {
        locations.add(location);
    }
}
