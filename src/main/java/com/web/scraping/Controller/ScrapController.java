package com.web.scraping.Controller;

import com.web.scraping.dto.ScraperDTO;
import com.web.scraping.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ScrapController {


    private final ScraperService service;

    @GetMapping("/get/{product}")
    public Set<ScraperDTO> getProduto(@PathVariable String product) {
        return service.getLinks(product);
    }
}
