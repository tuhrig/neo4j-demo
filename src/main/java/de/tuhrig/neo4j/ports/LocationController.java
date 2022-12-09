package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.location.Location;
import de.tuhrig.neo4j.domain.location.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private final LocationRepository locationRepository;

    @GetMapping(path = "/locations", produces = APPLICATION_JSON_VALUE)
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @PostMapping(path = "/locations", produces = APPLICATION_JSON_VALUE)
    public LocationCreatedDto createLocation(@RequestBody CreateLocationDto dto) {
        var id = locationRepository.save(dto.getStreet(), dto.getHouseNumber(), dto.getCity());
        logger.info("Saved new location. [id={}]", id);
        var response = new LocationCreatedDto();
        response.setId(id);
        return response;
    }

    @Data
    public static class CreateLocationDto {
        private String street;
        private String houseNumber;
        private String city;
    }

    @Data
    public static class LocationCreatedDto {
        private String id;
    }
}
