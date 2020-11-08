package pt.com.devdojo.awesome.endpoint;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.com.devdojo.awesome.error.ResourceNotFoundException;
import pt.com.devdojo.awesome.model.Student;
import pt.com.devdojo.awesome.model.User;
import pt.com.devdojo.awesome.repository.StudentRepository;
import pt.com.devdojo.awesome.repository.UserRepository;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.Arrays.asList;

@RestController
@RequestMapping("v1")
public class StudentEndpoint {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint( StudentRepository studentRepository) {

        this.studentRepository = studentRepository;

    }

    @GetMapping(path = "protected/students")
    public ResponseEntity<?> listAll(Pageable pageable){

        return new ResponseEntity<>(studentRepository.findAll(pageable),HttpStatus.OK);
    }


    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println(userDetails);

        verifyIfStudentExist(id);
        return new ResponseEntity<>(studentRepository.findOne(id), HttpStatus.OK );
    }

    @PostMapping(path = "admin/students")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save (@Valid @RequestBody Student student){

        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete (@PathVariable Long id){
        verifyIfStudentExist(id);
        studentRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
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
