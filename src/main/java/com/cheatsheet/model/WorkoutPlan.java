package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlan {
    private int id;
    private String planName;   // ဥပမာ - Push Pull Leg (PPL)
    private String planType;   // ဥပမာ - 3 Days Split / Bulk
    private String description;
    private String imageUrl;
}