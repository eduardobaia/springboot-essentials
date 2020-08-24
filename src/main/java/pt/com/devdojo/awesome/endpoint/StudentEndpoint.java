package pt.com.devdojo.awesome.endpoint;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.com.devdojo.awesome.error.ResourceNotFoundException;
import pt.com.devdojo.awesome.model.Student;
import pt.com.devdojo.awesome.repository.StudentRepository;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.Arrays.asList;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint( StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){

        return new ResponseEntity<>(studentRepository.findAll(),HttpStatus.OK);
    }


    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){

        verifyIfStudentExist(id);
        return new ResponseEntity<>(studentRepository.findOne(id), HttpStatus.OK );
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save (@Valid @RequestBody Student student){

        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        verifyIfStudentExist(id);
        studentRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update (@RequestBody Student student){
        verifyIfStudentExist(student.getId());
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExist( Long id){
             if(studentRepository.findOne(id) == null)
            throw new ResourceNotFoundException("Student not found for id:" + id);
    }

}
