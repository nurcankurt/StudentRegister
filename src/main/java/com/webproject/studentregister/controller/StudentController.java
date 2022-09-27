package com.webproject.studentregister.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webproject.studentregister.model.Image;
import com.webproject.studentregister.model.Student;
import com.webproject.studentregister.repository.StudentRepository;
import com.webproject.studentregister.service.ImageService;
import com.webproject.studentregister.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path ="/api")
public class StudentController {

    //localhost:8080/api/students

    private final StudentService service; //IOC
    private final ImageService imageService;

    @PostMapping(path = "/students", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<Student> save(@RequestParam String str,@RequestParam(value = "image",required = false) MultipartFile file) throws JsonProcessingException {
        Student student = new ObjectMapper().readValue(str, Student.class);
        if(file!=null) {
            Image image = imageService.save(file);
            student.setImage(image.getName());
        }
        return new ResponseEntity<>(service.save(student), HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> fetchStudents(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping(path = "/students", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<Student> update(@RequestParam String str,@RequestParam(value = "image",required = false) MultipartFile file) throws JsonProcessingException{
        Student student = new ObjectMapper().readValue(str, Student.class);
        if(file!=null) {
            Image image = imageService.save(file);
            student.setImage(image.getName());
        }
        return new ResponseEntity<>(service.update(student), HttpStatus.OK);
    }


    @DeleteMapping("/students/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value="id" )Long id){
        service.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @GetMapping(path = "/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id){
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

   @GetMapping(path = "/images/{id}")
    public ResponseEntity<String> getByIdImage(@PathVariable(value = "id") Long id){
        if(service.getById(id).getImage()!=null){
            String imageName = service.getById(id).getImage();
            return new ResponseEntity<>(imageService.viewImage(imageName), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
