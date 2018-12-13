package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.EndpointStyle;
import by.bntu.diploma.diagram.repository.EndpointStyleRepository;
import by.bntu.diploma.diagram.service.EndpointStyleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointStyleServiceImpl implements EndpointStyleService {

    private EndpointStyleRepository endpointStyleRepo;

    @Override
    @Transactional
    public EndpointStyle save(@Valid EndpointStyle endpointStyle) {
        return this.endpointStyleRepo.save(endpointStyle);
    }
}
