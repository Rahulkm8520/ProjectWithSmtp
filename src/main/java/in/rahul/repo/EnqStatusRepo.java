package in.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahul.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
