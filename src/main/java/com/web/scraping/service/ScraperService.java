package com.web.scraping.service;

import com.web.scraping.dto.ScraperDTO;

import java.util.Set;

public interface ScraperService {

    Set<ScraperDTO> getLinks(String product);

}
