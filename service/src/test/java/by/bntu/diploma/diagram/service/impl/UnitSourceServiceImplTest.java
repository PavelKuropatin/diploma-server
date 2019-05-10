package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitSourceServiceImplTest {

    private static final long TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    private final Answer<Source> setSourceUuid = invocation -> {
        Source source = invocation.getArgument(0);
        if (source == null) {
            throw new NullPointerException();
        }
        source.setUuid(UUID.randomUUID().toString());
        return source;
    };

    private final Answer<List<Source>> setSourcesUuid = invocation -> {
        List<Source> sources = invocation.getArgument(0);
        if (sources.contains(null)) {
            throw new NullPointerException();
        }
        sources.forEach(connection -> connection.setUuid(UUID.randomUUID().toString()));
        return sources;
    };

    @InjectMocks
    private SourceServiceImpl sourceService;

    @Mock
    private SourceRepository sourceRepository;

    @Test
    @DisplayName("save valid source")
    void saveSource_validSource_returnObj() {
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.count()).thenReturn(0L, 1L);
        Source source = Source.builder()
                .build();
        assertNull(source.getUuid());
        assertEquals(0, sourceRepository.count());
        sourceService.saveSource(source);
        assertNotNull(source.getUuid());
        assertEquals(1, sourceRepository.count());
    }

    @Test
    @DisplayName("save valid sources")
    void saveAllSources_validSources_returnObj() {
        when(sourceRepository.saveAll(anyList())).thenAnswer(setSourcesUuid);
        when(sourceRepository.count()).thenReturn(0L, TEST_SIZE);

        List<Source> sources = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Source.builder().build())
                .collect(Collectors.toList());

        assertEquals(0, sourceRepository.count());

        sourceService.saveAllSources(sources);

        List<String> actualUuids = sources.stream().map(Source::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, sourceRepository.count());
    }


    @Test
    @DisplayName("new source")
    void newSource__returnObj() {
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.count()).thenReturn(0L, 1L);

        assertEquals(0, sourceRepository.count());

        Source source = sourceService.newSource();

        assertNotNull(source.getUuid());
        assertEquals(1, sourceRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findBySourceUuid_validUUID_returnObj() {
        Source expected = Source.builder()
                .build();
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.findById(any(String.class))).thenReturn(Optional.of(expected));

        sourceService.saveSource(expected);

        assertNotNull(expected.getUuid());

        Source actual = sourceService.findBySourceUuid(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find by null uuid")
    void findBySourceUuid_nullUUID_returnObj() {
        when(sourceRepository.findById(nullable(String.class))).thenReturn(Optional.empty());

        Source actual = sourceService.findBySourceUuid(null);

        assertNull(actual);
    }


    @Test
    @DisplayName("find by non existent uuid")
    void findBySourceUuid_nonExistentUuid_returnObj() {
        when(sourceRepository.findById(any(String.class))).thenReturn(Optional.empty());

        Source actual = sourceService.findBySourceUuid(RANDOM_UUID);

        assertNull(actual);
    }

}