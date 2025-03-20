package com.apidemo.controller;

import com.apidemo.entity.Registration;
import com.apidemo.payload.RegistrationDto;
import com.apidemo.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    //http://localhost:8080/api/v1/register
    //public String createRegistration(@RequestBody RegistrationDto registrationDto) {
    @PostMapping
    //public ResponseEntity<RegistrationDto> createRegistration(
    public ResponseEntity<?> createRegistration(
            @Valid @RequestBody RegistrationDto registrationDto,
            BindingResult result
    ) {//@RequestBody copy the json data that you supply through postman via url into dto
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());//more advisable so that frontend easily handle it
            //return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage()); to get the default message
        }
        RegistrationDto registration=registrationService.createRegistration(registrationDto);
        //return new ResponseEntity<RegistrationDto>(registration, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).header("Custom-Header","Value").body(registration);
        //return "Registration successful with ID: " + registration.getId();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRegistration(@RequestParam long id){
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>("Registration deleted successfully with ID: " + id, HttpStatus.OK);
        //return "Registration deleted successfully with ID: " + id;
    }

    //http://localhost:8080/api/v1/register/2
    @PutMapping("{id}")    //whatever given inside putmapping is as same as variable
    public String updateRegistration(@PathVariable long id,@RequestBody RegistrationDto registrationDto) {
        registrationService.uppdatedRegistration(id,registrationDto);
        return "Registration updated successfully with ID: " + id;
    }

    //http://localhost:8080/api/v1/register?pageNo=1&pageSize=2&sortBy=title
    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations(
            @RequestParam(name ="pageNo", required = false,defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false,defaultValue = "3") int pageSize,
            @RequestParam(name= "sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(name= "sortDir",required = false,defaultValue = "asc") String sortDir
    ){
        List<RegistrationDto> registrations = registrationService.getAllRegistrations(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(registrations,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistration(@PathVariable long id){
        Registration reg = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(reg,HttpStatus.OK);
    }
}
