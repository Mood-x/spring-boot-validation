package com.example.tracker_system.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Project {

    @NotEmpty(message = "ID cannot be null ")
    @Size(min = 2, message = "ID length should be more than 2")
    private String id;
    
    @NotEmpty(message = "Title cannot be null ")
    @Size(min = 8, message = "Title length should be more than 8")
    private String title;

    @NotEmpty(message = "Description cannot be null ")
    @Size(min = 15, message = "Description length should be more than 15")
    private String description;

    @NotEmpty(message = "Status cannot be null")
    @Pattern(regexp = "^(Not Started|In Progress|Completed)$", message = "Status must be either 'Not Started', 'In Progress', or 'Completed'")

    private String status;

    @NotEmpty(message = "Company name cannot be null ")
    @Size(min = 6, message = "Company name length should be more than 6")
    private String companyName; 
}
