package com.luxoft.sdemenkov.testutils;


import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.web.dto.CountryDto;

public class CountryDtoGenerator {
    public static CountryDto getCountryDtoForTest() {
        return new CountryDto(new Country(1, "Country"));
    }
}
