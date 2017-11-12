package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class CountryControllerTest {
    @InjectMocks
    CountryController countryController;
    private MockMvc mockMvc;
    @Mock
    private CountryService countryService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    public void getAllCountries() throws Exception {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(1, "cuntry Name"));
        countryList.add(new Country(1, "cuntry Name"));
        countryList.add(new Country(1, "cuntry Name"));
        when(countryService.getAllCountries()).thenReturn(countryList);
        mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("cuntry Name"));
    }

}