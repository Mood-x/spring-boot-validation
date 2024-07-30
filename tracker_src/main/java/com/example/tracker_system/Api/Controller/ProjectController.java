package com.example.tracker_system.Api.Controller;

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

import com.example.tracker_system.Api.ApiResponse;
import com.example.tracker_system.Model.Project;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    List<Project> projects = new ArrayList<>();

    @GetMapping()
    public List<Project> displayProjects(){
        return projects; 
    }
    
    @PostMapping("/add")
    public ResponseEntity createProject(@Valid @RequestBody Project project, Errors err){
        if(err.hasErrors()){
            
            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message)); 
        }

        projects.add(project); 
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(project.getTitle() + " Added to project list"));  
    }

    @PutMapping("/{index}/update")
    public ResponseEntity updateProject(@PathVariable int index, @Valid @RequestBody Project project, Errors err){
        if(err.hasErrors()){

            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message));
        }

        projects.set(index, project); 
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(project.getTitle() + " Project updated"));  
    }

    @DeleteMapping("/{index}/delete")
    public ResponseEntity deleteProject(@PathVariable int index){
        Project project = projects.get(index);

        if(projects.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(project.getTitle() + " Array is empty"));
        }

        projects.remove(project);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(project.getTitle() + " Deleted from projects list "));
    }

    @PutMapping("/{index}/status")
    public ResponseEntity changeStatus(@PathVariable int index){
        Project project = projects.get(index); 
        if(project.getStatus().equalsIgnoreCase("Not Started")){
            project.setStatus("In Progress");
        }else if(project.getStatus().equalsIgnoreCase("In Progress")){
            project.setStatus("Complated");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(project.getTitle() + " Change status to " + project.getStatus()));
    }

    @GetMapping("/search")
    public ResponseEntity searchProject(@Valid @RequestBody String title){
        ArrayList titleProjects = new ArrayList<>();
        for(Project project : projects){
            if(project.getTitle().equalsIgnoreCase(title)){
                titleProjects.add(project);
            }
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(titleProjects.isEmpty() ? "Project not found" : titleProjects.toString()));
    }

    @GetMapping("/company")
    public ApiResponse displayCompanyProjects(@Valid @RequestBody String company){
        ArrayList companyProjects = new ArrayList<>(); 

        for(Project project : projects){
            if(project.getCompanyName().equalsIgnoreCase(company)){
                companyProjects.add(project); 
            }
        }
        return new ApiResponse(companyProjects.isEmpty() ? "Not found projects " : " All projects for this company " + companyProjects); 
    }
}
