package com.web.scraping.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScraperDTO {

    private String empresa;

    private List<ScraperItemDTO> items = new ArrayList<>();
}
