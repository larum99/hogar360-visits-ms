package com.hogar360.visits.visits.application.services.impl;

import com.hogar360.visits.commons.configurations.utils.Constants;
import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.mappers.VisitDtoMapper;
import com.hogar360.visits.visits.application.services.VisitService;
import com.hogar360.visits.visits.domain.ports.in.VisitServicePort;
import com.hogar360.visits.visits.domain.ports.in.RoleValidatorPort;
import com.hogar360.visits.visits.domain.model.VisitModel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitServicePort visitServicePort;
    private final VisitDtoMapper visitDtoMapper;
    private final RoleValidatorPort roleValidatorPort;

    @Override
    public SaveVisitResponse saveVisit(SaveVisitRequest request, String token) {
        Long userId = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);

        VisitModel visitModel = visitDtoMapper.requestToModel(request);

        visitServicePort.saveVisit(
                visitModel,
                userId,
                role
        );

        return new SaveVisitResponse(Constants.SAVE_VISIT_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}