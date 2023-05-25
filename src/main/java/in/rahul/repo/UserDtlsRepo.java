package in.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rahul.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>{
	//check the table mailid is present or not 
	public UserDtlsEntity findByEmail(String email); 
	//check the table mail and pwd is present or not
    public UserDtlsEntity findByEmailAndPwd(String email, String pwd);
    
}
