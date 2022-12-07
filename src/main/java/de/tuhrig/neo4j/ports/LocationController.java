package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.Location;
import de.tuhrig.neo4j.domain.LocationRepository;
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
public class LocationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LocationRepository locationRepository;

    public LocationController(
            LocationRepository locationRepository
    ) {
        this.locationRepository = locationRepository;
    }

    @GetMapping(path = "/locations", produces = APPLICATION_JSON_VALUE)
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @PostMapping(path = "/locations", produces = APPLICATION_JSON_VALUE)
    public Long createLocation(@RequestBody LocationDto dto) {
        var id = locationRepository.save(dto);
        logger.info("Saved new location. [id={}]", id);
        return id;
    }

    @AllArgsConstructor
    @Data
    public static class LocationDto {
        private String street;
        private String houseNumber;
        private String city;
    }
}
