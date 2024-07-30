package com.example.event_system.API.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_system.API.ApiResponse;
import com.example.event_system.Model.Events;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")

public class EventController {
    List<Events> events = new ArrayList<>(); 
    

    @GetMapping
    public ResponseEntity displayEvents(){
        
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Events List it's empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(events); 
    }



    @PostMapping("/add")
    public ResponseEntity createEvent(@Valid @RequestBody Events event, Errors err){

        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message)); 
        }
        events.add(event);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(event.getId() + " Added to events list")); 

    }


    @PutMapping("/{index}/update")
    public ResponseEntity updateEvent(@PathVariable int index, @Valid  @RequestBody Events event, Errors err){
        
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message));
        }

        events.set(index, event); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(event.getId() + " Event updated"));
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity DeleteEvent(@PathVariable String id){
        for(Events event: events){
            if(event.getId().equalsIgnoreCase(id)){
                events.remove(event); 
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Evenet with this ID: (" + id + "),  Deleted"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Evenet with this ID: (" + id + "), Not found"));
    }



    @PutMapping("/{id}/changeCapacity")
    public ResponseEntity changeCapacity(@PathVariable String id, @Valid @RequestBody int capacity, Errors err){

        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message));
        }

        for(Events event : events){
            if(event.getId().equalsIgnoreCase(id)){
                event.setCapacity(capacity);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Event capascity changed to " + capacity));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Evenet with this ID: (" + id + "), Not found"));
    }

    @GetMapping("/search")
    public ApiResponse changeCapacity(@Valid @RequestBody String id){
        for(Events event : events){
            if(event.getId().equalsIgnoreCase(id)){
                return new ApiResponse("Event" + event.toString()); 
            }
        }
        return new ApiResponse("Evenet with this ID: (" + id + ") not found"); 
    }
}
