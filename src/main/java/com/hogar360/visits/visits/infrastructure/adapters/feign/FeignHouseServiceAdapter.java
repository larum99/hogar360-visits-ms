package com.hogar360.visits.visits.infrastructure.adapters.feign;

import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.infrastructure.feigns.HouseFeignClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeignHouseServiceAdapter implements HouseServicePort {

    private final HouseFeignClient houseFeignClient;

    @Override
    public Optional<Long> getOwnerId(Long houseId) {
        try {
            Long ownerId = houseFeignClient.getOwnerId(houseId);

            return Optional.of(ownerId);

        } catch (FeignException.NotFound e) {
            return Optional.empty();
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio Houses al obtener propietario.", e);
        }
    }

    @Override
    public List<Long> getHouseIdsByLocation(Long cityId, String sector) {
        return houseFeignClient.getHouseIdsByLocation(cityId, sector);
    }

}