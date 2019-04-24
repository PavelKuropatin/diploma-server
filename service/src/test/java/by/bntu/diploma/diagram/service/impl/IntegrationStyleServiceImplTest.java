package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Style;
import by.bntu.diploma.diagram.repository.StyleRepository;
import by.bntu.diploma.diagram.service.StyleService;
import by.bntu.diploma.diagram.service.impl.config.TestConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationStyleServiceImplTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);

    @Autowired
    private StyleService styleService;

    @Autowired
    private StyleRepository styleRepository;

    @Test
    @DisplayName("save valid style")
    void saveStyle_validObj_returnObj() {
        Style style = Style.builder()
                .sourceStyle(VALID_STR)
                .sourceAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .build();
        assertNull(style.getUuid());
        assertEquals(0, styleRepository.count());
        styleService.saveStyle(style);
        assertEquals(1L, (long) style.getUuid());
        assertEquals(1, styleRepository.count());
    }

    @Test
    @DisplayName("save null style")
    void saveStyle_nullObj_returnException() {
        assertThrows(DataAccessException.class, () -> styleService.saveStyle(null));
    }

}