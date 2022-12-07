package de.tuhrig.neo4j.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Node("shop")
public class Shop {

    @Id
    private String id;

    private String name;

    private List<Location> locations = new ArrayList<>();

    private Shop() {
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Shop(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void locatedAt(Location location) {
        locations.add(location);
    }
}
