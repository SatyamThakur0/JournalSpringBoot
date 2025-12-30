package com.tanx.journal.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class QuoteRes {
    @JsonProperty("quote")
    public String quote;
    @JsonProperty("author")
    public String author;
    @JsonProperty("work")
    public String work;
    @JsonProperty("categories")
    public ArrayList<String> categories;
}