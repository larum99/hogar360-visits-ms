package com.hogar360.visits.visits.application.services;

import com.hogar360.visits.visits.application.dto.request.ListVisitsRequest;
import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.PagedVisitResponse;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;

import java.util.List;

public interface VisitService {
    SaveVisitResponse saveVisit(SaveVisitRequest request, String token);
    PagedVisitResponse listVisits(ListVisitsRequest request);
    List<VisitResponse> getAvailableVisitsByHouseId(Long houseId);
}
