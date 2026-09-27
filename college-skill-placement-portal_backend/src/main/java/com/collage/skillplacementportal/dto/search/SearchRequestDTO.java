package com.collage.skillplacementportal.dto.search;

public class SearchRequestDTO {

    private String keyword;

    public SearchRequestDTO() {
    }

    public SearchRequestDTO(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
