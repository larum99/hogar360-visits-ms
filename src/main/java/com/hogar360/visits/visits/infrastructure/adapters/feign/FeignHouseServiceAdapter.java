package com.hogar360.visits.visits.infrastructure.adapters.feign;

import com.hogar360.visits.visits.domain.ports.out.HouseServicePort; // Importar el Driven Port del dominio
import com.hogar360.visits.visits.infrastructure.feigns.HouseFeignClient; // Importar la interfaz Feign Client

import feign.FeignException; // Importar FeignException
import lombok.RequiredArgsConstructor; // Importar Lombok
import org.springframework.stereotype.Service; // O @Component

import java.util.Optional; // Importar Optional

@Service
@RequiredArgsConstructor
public class FeignHouseServiceAdapter implements HouseServicePort {

    private final HouseFeignClient houseFeignClient; // Spring inyectará la implementación generada por Feign

    @Override
    public Optional<Long> getOwnerId(Long houseId) {
        try {
            // Llama al método del Feign Client. Si la casa no existe (404), FeignException.NotFound será lanzada.
            Long ownerId = houseFeignClient.getOwnerId(houseId);

            // Si no hubo 404, la llamada fue exitosa y tenemos el ownerId.
            return Optional.of(ownerId);

        } catch (FeignException.NotFound e) {
            // Captura el error de infraestructura 404 y lo traduce a un Optional vacío para el dominio.
            return Optional.empty();
        } catch (FeignException e) {
            // Capturar otros errores de infraestructura (5xx, conexión, etc.)
            // Los envolvemos en una excepción de Runtime para no exponer FeignException al dominio.
            throw new RuntimeException("Error de comunicación con el servicio Houses al obtener propietario.", e);
        }
    }
}