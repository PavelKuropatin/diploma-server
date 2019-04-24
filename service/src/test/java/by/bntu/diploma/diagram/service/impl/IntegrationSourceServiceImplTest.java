package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.service.SourceService;
import by.bntu.diploma.diagram.service.impl.config.TestConfig;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationSourceServiceImplTest {

    private static final int TEST_SIZE = 3;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private SourceRepository sourceRepository;


    @Test
    @DisplayName("save valid source")
    void saveSource() {
        Source source = Source.builder()
                .connections(Collections.emptyList())
                .build();
        assertNull(source.getUuid());
        assertEquals(0, sourceRepository.count());
        sourceService.saveSource(source);
        assertEquals(1L, (long) source.getUuid());
        assertEquals(1, sourceRepository.count());
    }

    @Test
    @DisplayName("save valid sources")
    void saveAllSources_validSources_returnObj() {
        List<Source> sources = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Source.builder().build())
                .collect(Collectors.toList());

        sourceService.saveAllSources(sources);

        List<Long> actualUuids = sources.stream().map(Source::getUuid).sorted().collect(Collectors.toList());
        List<Long> expectedUuids = LongStream.range(1, TEST_SIZE + 1).boxed().collect(Collectors.toList());

        assertEquals(expectedUuids, actualUuids);
        assertEquals(TEST_SIZE, sourceRepository.count());
    }

    @Test
    @DisplayName("save invalid sources")
    void saveAllSources_containsInvalidSource_returnObj() {
        List<Source> sources = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Source.builder().build())
                .collect(Collectors.toList());

        sources.set(RandomUtils.nextInt(0, sources.size()), null);

        assertThrows(NullPointerException.class, () -> sourceService.saveAllSources(sources));

    }

    @Test
    @DisplayName("new source")
    void newSource() {
        assertEquals(0, sourceRepository.count());
        Source source = sourceService.newSource();
        assertEquals(1L, (long) source.getUuid());
        assertEquals(1, sourceRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findBySourceUUID_validUUID_returnObj() {
        Source expected = Source.builder()
                .connections(Collections.emptyList())
                .build();
        sourceService.saveSource(expected);

        assertEquals(1L, (long) expected.getUuid());

        Source actual = sourceService.findBySourceUUID(expected.getUuid());

        assertEquals(expected, actual);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find by non exist uuid", () -> assertNull(sourceService.findBySourceUUID(3L))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> sourceService.findBySourceUUID(null))
                ),
                dynamicTest(
                        "save null sources",
                        () -> assertThrows(NullPointerException.class, () -> sourceService.saveAllSources(null))
                ),
                dynamicTest(
                        "save invalid source",
                        () -> assertThrows(NullPointerException.class, () -> sourceService.saveSource(Source.builder().connections(null).build()))
                ),
                dynamicTest(
                        "save null source",
                        () -> assertThrows(NullPointerException.class, () -> sourceService.saveSource(null))
                )
        );
    }
}