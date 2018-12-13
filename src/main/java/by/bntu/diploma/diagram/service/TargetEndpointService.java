package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.TargetEndpoint;

import java.util.List;

public interface TargetEndpointService {

    TargetEndpoint save(TargetEndpoint targetEndpoint);

    List<TargetEndpoint> saveAll(List<TargetEndpoint> targetEndpoints);

    TargetEndpoint newTargetEndpoint();

}
