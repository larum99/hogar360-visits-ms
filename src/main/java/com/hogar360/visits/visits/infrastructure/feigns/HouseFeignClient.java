package com.hogar360.visits.visits.infrastructure.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "house-service", url = "http://localhost:8090")
public interface HouseFeignClient {

    @GetMapping("/api/v1/house/{houseId}/owner")
    Long getOwnerId(@PathVariable("houseId") Long houseId);
}