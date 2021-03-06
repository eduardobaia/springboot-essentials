package pt.com.devdojo.awesome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pt.com.devdojo.awesome.model.Student;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    List<Student> findByNameIgnoreCaseContaining (String name);

}
