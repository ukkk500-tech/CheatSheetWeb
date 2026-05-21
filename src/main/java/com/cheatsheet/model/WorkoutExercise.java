package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {
    private int id;
    private int planId;
    private String dayName;
    private String exerciseName;
    private String sets;
    private String reps;
    private String restTime;
}