package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDetail {
    private int id;
    private int planId;       // Foreign Key (Workout Plan ID)
    private String exerciseName;
    private String setsReps;  // ဥပမာ - 4 Sets x 12 Reps
    private String description;
    private String imageUrl;
}