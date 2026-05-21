package com.cheatsheet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    private int id;
    private int categoryId;
    private String subCategory; // Muscle Group Name
    private String imageUrl;    // Muscle Group Image
}