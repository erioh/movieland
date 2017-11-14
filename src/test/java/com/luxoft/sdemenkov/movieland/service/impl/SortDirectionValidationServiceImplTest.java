package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Pair;
import com.luxoft.sdemenkov.movieland.model.SortDirection;
import com.luxoft.sdemenkov.movieland.service.SortDirectionValidationService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class SortDirectionValidationServiceImplTest {
    @Test
    public void getValidationErrors() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        Pair<SortDirection, SortDirection> pair = sortDirectionValidationService.getValidationErrors(null, null);
        assertNull(pair.getFirstValue());
        assertNull(pair.getSecondValue());
        pair = sortDirectionValidationService.getValidationErrors("desc", null);
        assertEquals(SortDirection.DESC, pair.getFirstValue());
        assertEquals(null, pair.getSecondValue());
        pair = sortDirectionValidationService.getValidationErrors(null, "desc");
        assertEquals(null, pair.getFirstValue());
        assertEquals(SortDirection.DESC, pair.getSecondValue());
        pair = sortDirectionValidationService.getValidationErrors(null, "asc");
        assertEquals(null, pair.getFirstValue());
        assertEquals(SortDirection.ASC, pair.getSecondValue());

    }

    @Test(expected = RuntimeException.class)
    public void wrongAscDirection() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        Pair<SortDirection, SortDirection> pair = sortDirectionValidationService.getValidationErrors("asc", null);

    }

    @Test(expected = RuntimeException.class)
    public void wrongNameDirectionOne() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        Pair<SortDirection, SortDirection> pair = sortDirectionValidationService.getValidationErrors("desc1", null);

    }

    @Test(expected = RuntimeException.class)
    public void wrongNameDirectionTwo() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        Pair<SortDirection, SortDirection> pair = sortDirectionValidationService.getValidationErrors(null, "desc1");

    }

    @Test(expected = RuntimeException.class)
    public void wrongNumberOfSoring() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        Pair<SortDirection, SortDirection> pair = sortDirectionValidationService.getValidationErrors("desc", "desc");
    }

}