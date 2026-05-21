package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietDetail {
    private int id;
    private int planId;
    private String mealTime;
    private String foodItems;
    private String proteinGrams;
    private String calories;
}