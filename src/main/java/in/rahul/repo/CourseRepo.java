package in.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahul.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
