package de.tuhrig.neo4j.ports;

import de.tuhrig.neo4j.domain.shop.Shop;
import de.tuhrig.neo4j.domain.shop.ShopRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * What to see here:
 * <p>
 * - Usage of a simple DTO
 */
@RestController
@AllArgsConstructor
public class ShopController {

    private final ShopRepository shopRepository;

    @GetMapping(path = "/shops", produces = APPLICATION_JSON_VALUE)
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @PutMapping(path = "/shops/locations", produces = APPLICATION_JSON_VALUE)
    public void appendLocation(@RequestBody AppendLocationDto dto) {
        shopRepository.appendLocation(dto.shopId, dto.locationId);
    }

    @Data
    static class AppendLocationDto {
        private String shopId;
        private String locationId;
    }
}
