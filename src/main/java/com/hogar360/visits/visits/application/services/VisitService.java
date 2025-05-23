package com.hogar360.visits.visits.application.services;

import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;

public interface VisitService {
    SaveVisitResponse saveVisit(SaveVisitRequest request, String token);
}
