package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Style;
import by.bntu.diploma.diagram.repository.StyleRepository;
import by.bntu.diploma.diagram.service.StyleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StyleServiceImpl implements StyleService {

    private StyleRepository styleRepo;

    @Override
    @Transactional
    public Style saveStyle(Style style) {
        return this.styleRepo.save(style);
    }
}
