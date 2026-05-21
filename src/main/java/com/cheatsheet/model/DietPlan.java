package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietPlan {
    private int id;
    private int categoryId; // Gym သို့မဟုတ် Home Category နဲ့ ချိတ်ဖို့
    private String planName; // ဥပမာ - Keto Diet, Muscle Building Diet
    private String planType; // ဥပမာ - High Protein, Low Carb
    private String imageUrl;
}