package com.web.scraping.service.impl;

import com.web.scraping.dto.ScraperDTO;
import com.web.scraping.dto.ScraperItemDTO;
import com.web.scraping.service.ScraperService;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Getter
@Setter
public class ScraperServiceImpl implements ScraperService {

    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;

    @Override
    public Set<ScraperDTO> getLinks(String product) {

        Set<ScraperDTO> responseDTOS = new HashSet<>();

        var cont = new AtomicInteger(0);
        urls.forEach(url -> {
            cont.incrementAndGet();
            if (cont.get() == 1) {
                extractDataFromCanalConstrucao(responseDTOS, url + product);
                 } else if (cont.get() == 2) {
                        extractDataFromCoral(responseDTOS, url + product);
                 } else if (cont.get() == 3) {
                         extractDataFromTupan(responseDTOS, url + product);
                 } else if (cont.get() == 4) {
                         extractDataFromFerreiraCosta(responseDTOS, url + product);
                 } else if (cont.get() == 5) {
                         extractDataFromVeneza(responseDTOS, url + product);
            }
        });

        return responseDTOS;
    }

    private void extractDataFromCanalConstrucao(Set<ScraperDTO> responseDTOS, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsClassProduct = document.select(".product");
            var responseDTO = new ScraperDTO();
            responseDTO.setEmpresa("Canal");

            for (Element elementClassProduct : elementsClassProduct) {
                var item = new ScraperItemDTO();

                Element elementMainImgBox = elementClassProduct.selectFirst(".main-img-box");
                if (elementMainImgBox != null) {
                    item.setTitle(elementMainImgBox.attr("title"));
                    item.setUrl("https://www.canalconstrucao.com.br" + elementMainImgBox.attr("href"));
                }

                Element elementValueFor = elementClassProduct.selectFirst(".value-for.color-value-for");

                if (elementValueFor != null) {
                    item.setPrice(elementValueFor.text());
                }
                responseDTO.getItems().add(item);
            }

            responseDTOS.add(responseDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void extractDataFromCoral(Set<ScraperDTO> responseDTOS, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsProductsListBody = document.select(".products-list__body");
            Elements elementsItems = elementsProductsListBody.select(".products-list__item");

            var responseDTO = new ScraperDTO();
            responseDTO.setEmpresa("Coral");

            for (Element elementItem : elementsItems) {

                var item = new ScraperItemDTO();

                Element elementProductCard = elementItem.getElementsByClass("product-card").first();
                Element elementProductCardInfo = elementProductCard.getElementsByClass("product-card__info text-center").first();
                Element elementProductCardName = elementProductCardInfo.getElementsByClass("product-card__name").first();
                Elements elementTagA = elementProductCardName.getElementsByTag("a");

                if (elementTagA != null) {
                    item.setTitle(elementTagA.attr("data-nome"));
                    item.setUrl(elementTagA.attr("href"));
                    item.setPrice(elementTagA.attr("data-preco"));
                }

                responseDTO.getItems().add(item);

            }
            responseDTOS.add(responseDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void extractDataFromTupan(Set<ScraperDTO> responseDTOS, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsProductsListBody = document.select(".products-list__body");
            Elements elementsItems = elementsProductsListBody.select(".products-list__item");

            var responseDTO = new ScraperDTO();
            responseDTO.setEmpresa("Tupan");

            for (Element elementItem : elementsItems) {

                var item = new ScraperItemDTO();

                Element elementProductCard = elementItem.getElementsByClass("product-card").first();
                Element elementProductCardInfo = elementProductCard.getElementsByClass("product-card__info text-center").first();
                Element elementProductCardName = elementProductCardInfo.getElementsByClass("product-card__name").first();
                Elements elementTagA = elementProductCardName.getElementsByTag("a");

                if (elementTagA != null) {
                    item.setTitle(elementTagA.attr("data-nome"));
                    item.setUrl(elementTagA.attr("href"));
                    item.setPrice(elementTagA.attr("data-preco"));
                }

                responseDTO.getItems().add(item);

            }
            responseDTOS.add(responseDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void extractDataFromFerreiraCosta(Set<ScraperDTO> responseDTOS, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsProductsListBody = document.select(".styles__PlaylistStyled-sc-f436b775-0");

            var responseDTO = new ScraperDTO();
            responseDTO.setEmpresa("Ferreira Costa");

            for (Element elementItem : elementsProductsListBody) {

                var item = new ScraperItemDTO();

                        Element elementProductCard = elementItem.getElementsByClass("styles__CardContainer-sc-f436b775-1").first();
                Element elementProductCardInfo = elementProductCard.getElementsByClass("styles__ProductCardTagPriceOnlySiteText-sc-ab92a1d-25").first();
                Element elementProductCardName = elementProductCardInfo.getElementsByClass("styles__ProductCardTagPriceOnlySiteText-sc-ab92a1d-25").first();
               Element elementProductCardContent = elementProductCardName.getElementsByClass("styles__ProductCardInfoContainer-sc-ab92a1d-12").first();
                Elements elementTagH3 = elementProductCardContent.getElementsByTag("h3");

                if (elementProductCardInfo != null) {
                    item.setUrl(elementProductCard.attr("href"));
                    item.setPrice(elementTagH3.text());
                }

                responseDTO.getItems().add(item);

            }
            responseDTOS.add(responseDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void extractDataFromVeneza(Set<ScraperDTO> responseDTOS, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elementsProducts = document.select(".cdz-products-grid");
            Elements elementsProductsGrid = elementsProducts.select(".item");

            var responseDTO = new ScraperDTO();
            responseDTO.setEmpresa("Veneza");

            for (Element elementItem : elementsProductsGrid) {

                var item = new ScraperItemDTO();

                Element elementProductInfo = elementItem.getElementsByClass("product-item-info").first();
                Element elementRegularPrice = elementProductInfo.getElementsByClass("regular-price").first();
                if (elementRegularPrice == null){
                    elementRegularPrice = elementProductInfo.getElementsByClass("special-price").first();
                }
                Element elementPriceBox = elementProductInfo.getElementsByClass("product-item-link").first();
                Elements elementTagSpan = elementRegularPrice.getElementsByTag("span");
                Elements elementTagA = elementProductInfo.getElementsByTag("a");

                if (elementTagA != null) {
                    item.setTitle(elementPriceBox.text());
                    item.setUrl(elementTagA.attr("href"));
                    item.setPrice(elementTagSpan.text());
                }

                responseDTO.getItems().add(item);

            }

            responseDTOS.add(responseDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}



