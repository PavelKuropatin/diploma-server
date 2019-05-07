package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Style;
import by.bntu.diploma.diagram.repository.StyleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitStyleServiceImplTest {

    @InjectMocks
    StyleServiceImpl styleService;

    @Mock
    StyleRepository styleRepository;


    @Test
    @DisplayName("save valid style")
    void saveStyle_validObj_returnObj() {
        Style style = Style.builder().build();
        when(styleRepository.count()).thenReturn(0L, 1L);
        when(styleRepository.save(any(Style.class))).thenAnswer((Answer<Style>) invocation -> {
            Style style1 = invocation.getArgument(0);
            style1.setUuid(UUID.randomUUID().toString());
            return style1;
        });

        assertNull(style.getUuid());
        assertEquals(0, styleRepository.count());

        styleService.saveStyle(style);

        assertNotNull(style.getUuid());
        assertEquals(1, styleRepository.count());
    }

    @Test
    @DisplayName("save null style")
    void saveStyle_nullObj_returnException() {
        when(styleRepository.save(nullable(Style.class))).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> styleService.saveStyle(null));
        verify(styleRepository).save(nullable(Style.class));
    }

}