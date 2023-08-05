package com.example.art_ordering.dto;


import lombok.Data;

@Data
public class productDTO {
    private long id;
    private String name;

    private int categoryId;
    private  Double price;

    private String Description;

    private String imageName;
}
