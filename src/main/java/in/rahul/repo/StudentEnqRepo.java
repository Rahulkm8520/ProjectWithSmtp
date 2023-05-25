package in.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahul.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{
 
	//StudentEnqEntity findByEnqId(Integer enqId);
	
}
  