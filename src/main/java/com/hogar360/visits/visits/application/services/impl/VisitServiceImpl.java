package com.hogar360.visits.visits.application.services.impl;

import com.hogar360.visits.commons.configurations.utils.Constants;
import com.hogar360.visits.visits.application.dto.request.ListVisitsRequest;
import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.PagedVisitResponse;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;
import com.hogar360.visits.visits.application.mappers.VisitCriteriaMapper;
import com.hogar360.visits.visits.application.mappers.VisitDtoMapper;
import com.hogar360.visits.visits.application.services.VisitService;
import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.ports.in.VisitServicePort;
import com.hogar360.visits.visits.domain.ports.in.RoleValidatorPort;
import com.hogar360.visits.visits.domain.ports.out.HouseServicePort; // 👈 IMPORTANTE
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitServicePort visitServicePort;
    private final VisitDtoMapper visitDtoMapper;
    private final VisitCriteriaMapper visitCriteriaMapper;
    private final RoleValidatorPort roleValidatorPort;
    private final HouseServicePort houseServicePort;

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

    @Override
    public PagedVisitResponse listVisits(ListVisitsRequest request) {
        VisitCriteria criteria = visitCriteriaMapper.requestToCriteria(request);

        if (request.cityId() != null || request.sector() != null) {
            List<Long> houseIds = houseServicePort.getHouseIdsByLocation(
                    request.cityId(),
                    request.sector()
            );

            criteria = new VisitCriteria(
                    criteria.getStartDateTime(),
                    criteria.getEndDateTime(),
                    criteria.getCityId(),
                    criteria.getSector(),
                    criteria.getPage(),
                    criteria.getSize(),
                    criteria.getSortBy(),
                    criteria.getSortDirection(),
                    houseIds
            );
        }

        PageResult<VisitModel> visitPage = visitServicePort.listVisits(criteria);
        List<VisitResponse> visitResponses = visitDtoMapper.modelToResponseList(visitPage.getContent());

        return new PagedVisitResponse(
                visitResponses,
                visitPage.getTotalElements(),
                visitPage.getTotalPages(),
                visitPage.getCurrentPage(),
                visitPage.getPageSize(),
                visitPage.isFirst(),
                visitPage.isLast()
        );
    }

    @Override
    public List<VisitResponse> getAvailableVisitsByHouseId(Long houseId) {
        List<VisitModel> availableVisits = visitServicePort.getAvailableVisitsByHouseId(houseId);
        return visitDtoMapper.modelToResponseList(availableVisits);
    }

}
