package com.example.event_system.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Events {

    @NotEmpty(message = "ID cannot be null")
    @Size(min = 2, message = "ID lenght should be more than 2")
    private String id;

    @NotEmpty(message = "Description cannot be null")
    @Size(min = 15, message = "Description lenght should be more than 15")
    private String description; 

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 25, message= "Capacity must be more than 25")
    @Pattern(regexp = "^[0-9]+$", message = "Capacity must be a numbers")
    private String capacity; 
    
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime startDate; 

    @JsonFormat(pattern="yyyy-MM-dd")

    private LocalDateTime endDate; 
}
