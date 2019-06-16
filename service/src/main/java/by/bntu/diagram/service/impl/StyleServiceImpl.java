package by.bntu.diagram.service.impl;

import by.bntu.diagram.domain.Style;
import by.bntu.diagram.repository.StyleRepository;
import by.bntu.diagram.service.StyleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StyleServiceImpl implements StyleService {

    private StyleRepository styleRepository;

    @Override
    @Transactional
    public Style saveStyle(Style style) {
        return styleRepository.save(style);
    }

    @Override
    @Transactional
    public List<Style> saveAllStyles(List<@Valid Style> styles) {
        styles.stream()
                .filter(style -> style.getUuid() != null)
                .filter(style -> !styleRepository.existsById(style.getUuid()))
                .forEach(style -> style.setUuid(null));
        return styleRepository.saveAll(styles);
    }
}
