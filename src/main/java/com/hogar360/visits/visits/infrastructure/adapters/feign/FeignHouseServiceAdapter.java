package com.hogar360.visits.visits.infrastructure.adapters.feign;

import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.infrastructure.feigns.HouseFeignClient;
import com.hogar360.visits.visits.infrastructure.feigns.dto.HouseResponse;

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

            return Optional.ofNullable(ownerId);

        } catch (FeignException.NotFound e) {
            return Optional.empty();
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio Houses al obtener propietario.", e);
        }
    }

    @Override
    public Optional<String> getHouseStatus(Long houseId) {
        try {
            HouseResponse house = houseFeignClient.getHouseById(houseId);

            if (house == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(house.status());

        } catch (FeignException.NotFound e) {
            return Optional.empty();
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio Houses al obtener estado de la casa.", e);
        }
    }

    @Override
    public List<Long> getHouseIdsByLocation(Long cityId, String sector) {
        return houseFeignClient.getHouseIdsByLocation(cityId, sector);
    }

}