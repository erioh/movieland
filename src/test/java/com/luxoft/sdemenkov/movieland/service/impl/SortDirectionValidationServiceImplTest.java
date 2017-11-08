package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.service.SortDirectionValidationService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * Created by sergeydemenkov on 08.11.17.
 */
public class SortDirectionValidationServiceImplTest {
    @Test
    public void getValidationErrors() throws Exception {
        SortDirectionValidationService sortDirectionValidationService = new SortDirectionValidationServiceImpl();
        ResponseEntity<String> validationErrors = sortDirectionValidationService.getValidationErrors(null, null);
        assertNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors("desc", null);
        assertNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors(null, "desc");
        assertNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors(null, "asc");
        assertNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors("asc", null);
        assertNotNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors("desc", "desc");
        assertNotNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors("desc1", null);
        assertNotNull(validationErrors);
        validationErrors = sortDirectionValidationService.getValidationErrors(null, "desc1");
        assertNotNull(validationErrors);

    }

}