package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RisultatoCompetizioneDtoListToMatchRecordProcessorTest {

    private RisultatoCompetizioneDtoListToMatchRecordProcessor risultatoCompetizioneDtoListToMatchRecordProcessor;

    @BeforeEach
    public void setUp() {
        risultatoCompetizioneDtoListToMatchRecordProcessor = new RisultatoCompetizioneDtoListToMatchRecordProcessor();
    }

    @Test
    public void testProcessWithNonEmptyList() throws Exception {
        List<RisultatoCompetizioneDto> items = Arrays.asList("item1", "item2", "item3");
        assertEquals("item1", risultatoCompetizioneDtoListToMatchRecordProcessor.process(items));
        assertEquals("item2", risultatoCompetizioneDtoListToMatchRecordProcessor.process(null));
        assertEquals("item3", risultatoCompetizioneDtoListToMatchRecordProcessor.process(null));
        assertNull(risultatoCompetizioneDtoListToMatchRecordProcessor.process(null));
    }

    @Test
    public void testProcessWithEmptyList() throws Exception {
        List<String> items = Collections.emptyList();
        assertNull(risultatoCompetizioneDtoListToMatchRecordProcessor.process(items));
    }

    @Test
    public void testProcessWithNullList() throws Exception {
        assertNull(risultatoCompetizioneDtoListToMatchRecordProcessor.process(null));
    }
}
