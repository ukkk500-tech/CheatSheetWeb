package com.cheatsheet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter             // Getter, Setter, toString တို့ကို အလိုလိုထုတ်ပေးပါတယ်
@NoArgsConstructor   // Parameter မပါတဲ့ Constructor
@AllArgsConstructor  // Field အားလုံးပါတဲ့ Constructor
public class ExerciseDetail {
    
    private int id;             // Primary Key (exercise_details table)
    private int exerciseId;     // Foreign Key (exercises table ရဲ့ ID - ဥပမာ Chest ရဲ့ ID)
    private String exerciseName; // လေ့ကျင့်ခန်းအမည် (ဥပမာ - Bench Press)
    private String setsReps;     // Sets နဲ့ Repetitions (ဥပမာ - 3 Sets of 12 Reps)
    private String description;  // ကစားနည်း အသေးစိတ် ရှင်းလင်းချက်
    private String imageUrl;     // ဒီလေ့ကျင့်ခန်းရဲ့ ပုံလမ်းကြောင်း
}