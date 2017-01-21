package com.piggsoft.retrofit.spring.boot.autoconfigue;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by piggs on 2017/1/20.
 */
@ConfigurationProperties(prefix = "retrofit")
public class RetrofitProperties {

    public static final String JACKSON = "jackson";

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item{
        private String name;
        private String baseUrl;
        private String converter;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getConverter() {
            return converter;
        }

        public void setConverter(String converter) {
            this.converter = converter;
        }
    }

}
