package de.tuhrig.neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // Empty constructor required as of Neo4j API 2.0.5
@Node("shop")
public class Shop {

    @Id
    private String id;

    private String name;

    private List<Location> locations = new ArrayList<>();

    public Shop(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void locatedAt(Location location) {
        locations.add(location);
    }
}
