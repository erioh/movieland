package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import com.luxoft.sdemenkov.movieland.web.responce.AllCountryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    CountryService countryService;

    @RequestMapping(method = RequestMethod.GET)
    List<AllCountryDTO> getAllCountries() {
        long startTime = System.currentTimeMillis();
        List<AllCountryDTO> allCountryDTOList = new ArrayList<>();
        List<Country> countryList = countryService.getAllCountries();
        log.debug("Method getAllCountries. Filling countryList with coutries");
        for (Country country : countryList) {
            allCountryDTOList.add(new AllCountryDTO(country));
        }
        log.debug("Method getAllCountries.  It took {} ms", System.currentTimeMillis() - startTime);
        return allCountryDTOList;
    }
}
