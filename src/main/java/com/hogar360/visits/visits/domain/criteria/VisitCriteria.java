package com.hogar360.visits.visits.domain.criteria;

import java.time.LocalDateTime;
import java.util.List;

public class VisitCriteria {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Long cityId;
    private final String sector;
    private final int page;
    private final int size;
    private final String sortBy;
    private final String sortDirection;
    private final List<Long> houseIds;

    public VisitCriteria(LocalDateTime startDateTime, LocalDateTime endDateTime, Long cityId, String sector,
                         int page, int size, String sortBy, String sortDirection, List<Long> houseIds) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.cityId = cityId;
        this.sector = sector;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.houseIds = houseIds;
    }

    public List<Long> getHouseIds() {
        return houseIds;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getSector() {
        return sector;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}
