package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.SourceEndpoint;

import java.util.List;

public interface SourceEndpointService {

    SourceEndpoint save(SourceEndpoint sourceEndpoint);

    List<SourceEndpoint> saveAll(List<SourceEndpoint> sourceEndpoints);

    SourceEndpoint newSourceEndpoint();
}
