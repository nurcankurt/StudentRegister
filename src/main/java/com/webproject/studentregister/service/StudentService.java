package com.webproject.studentregister.service;

import com.webproject.studentregister.model.Student;
import com.webproject.studentregister.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final ImageService imageService;
    public Student save(Student student) {
        return repository.save(student);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student update(Student student) {
        if (student.getId() != null && !repository.existsById(student.getId())) {
            return null;
        }
        return repository.save(student);
    }

    @Transactional
    public void delete(@PathVariable Long id){
        String imageName = getById(id).getImage();
        imageService.delete(imageName);
        repository.deleteById(id);
    }

    public Student getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
