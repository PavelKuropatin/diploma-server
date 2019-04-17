package by.bntu.diploma.diagram.service.impl.unit;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.impl.SourceServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitSourceServiceImplTest {

    private static final long TEST_SIZE = 3L;
    private final Answer<Source> setSourceUuid = new Answer<Source>() {
        long sequence = 1;

        @Override
        public Source answer(InvocationOnMock invocation) {
            Source source = invocation.getArgument(0);
            if (source == null) {
                throw new NullPointerException();
            }
            source.setUuid(sequence++);
            return source;
        }
    };
    private final Answer<List<Source>> setSourcesUuid = new Answer<List<Source>>() {
        long sequence = 1;

        @Override
        public List<Source> answer(InvocationOnMock invocation) {
            List<Source> sources = invocation.getArgument(0);
            if (sources.contains(null)) {
                throw new NullPointerException();
            }
            sources.forEach(connection -> connection.setUuid(sequence++));
            return sources;
        }
    };
    @InjectMocks
    private SourceServiceImpl sourceService;

    @Mock
    private SourceRepository sourceRepository;

    @Mock
    private ConnectionService connectionService;

    @Test
    @DisplayName("save valid source")
    void saveSource_validSource_returnObj() {
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.count()).thenReturn(0L, 1L);
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
        when(sourceRepository.saveAll(anyList())).thenAnswer(setSourcesUuid);
        when(sourceRepository.count()).thenReturn(0L, TEST_SIZE);

        List<Source> sources = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Source.builder().build())
                .collect(Collectors.toList());

        assertEquals(0, sourceRepository.count());

        sourceService.saveAllSources(sources);

        List<Long> actualUuids = sources.stream().map(Source::getUuid).sorted().collect(Collectors.toList());
        List<Long> expectedUuids = LongStream.range(1, TEST_SIZE + 1).boxed().collect(Collectors.toList());

        assertEquals(expectedUuids, actualUuids);
        assertEquals(TEST_SIZE, sourceRepository.count());
    }


    @Test
    @DisplayName("new source")
    void newSource__returnObj() {
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.count()).thenReturn(0L, 1L);

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
        when(sourceRepository.save(any(Source.class))).thenAnswer(setSourceUuid);
        when(sourceRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        sourceService.saveSource(expected);

        assertEquals(1L, (long) expected.getUuid());

        Source actual = sourceService.findBySourceUUID(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find by null uuid")
    void findBySourceUUID_nullUUID_returnObj() {
        when(sourceRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());

        Source actual = sourceService.findBySourceUUID(null);

        assertNull(actual);
    }


    @Test
    @DisplayName("find by non existent uuid")
    void findBySourceUUID_nonExistentUUID_returnObj() {
        when(sourceRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Source actual = sourceService.findBySourceUUID(3L);

        assertNull(actual);
    }

}