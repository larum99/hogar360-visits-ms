package com.hogar360.visits.visits.domain.ports.out;

import java.util.Optional;

public interface HouseServicePort {
    Optional<Long> getOwnerId(Long houseId);
}
