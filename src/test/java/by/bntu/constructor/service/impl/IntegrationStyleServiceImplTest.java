package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Style;
import by.bntu.constructor.repository.StyleRepository;
import by.bntu.constructor.service.StyleService;
import by.bntu.constructor.service.impl.config.TestConfig;
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
                .inputStyle(VALID_STR)
                .inputAnchorStyle(VALID_STR)
                .outputStyle(VALID_STR)
                .outputAnchorStyle(VALID_STR)
                .build();
        assertNull(style.getUuid());
        assertEquals(0, styleRepository.count());
        styleService.saveStyle(style);
        assertNotNull(style.getUuid());
        assertEquals(1, styleRepository.count());
    }

    @Test
    @DisplayName("save null style")
    void saveStyle_nullObj_returnException() {
        assertThrows(DataAccessException.class, () -> styleService.saveStyle(null));
    }

}