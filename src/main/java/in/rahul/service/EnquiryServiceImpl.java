package in.rahul.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rahul.binding.DashboardResponse;
import in.rahul.binding.EnquiryForm;
import in.rahul.constants.AppConstants;
import in.rahul.entity.CourseEntity;
import in.rahul.entity.EnqStatusEntity;
import in.rahul.entity.StudentEnqEntity;
import in.rahul.entity.UserDtlsEntity;
import in.rahul.repo.CourseRepo;
import in.rahul.repo.EnqStatusRepo;
import in.rahul.repo.StudentEnqRepo;
import in.rahul.repo.UserDtlsRepo;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private StudentEnqRepo enqRepo;
	@Autowired
	private CourseRepo coursesRepo;
	@Autowired
	private EnqStatusRepo statusRepo;
	@Autowired
	private HttpSession session;
	
	
	/*@Override
	
	public DashboardResponse getDashboardData(Integer userId) {
		DashboardResponse response = new DashboardResponse();
	 
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userEntity=findById.get();
			
			//Based on enquiry 
			List<StudentEnqEntity>enquiries=userEntity.getEnquiries();
			Integer totalCnt=enquiries.size();
			
			Integer enrolledCnt=enquiries.stream()
			         .filter(e-> e.getEnqStatus().equals("Enrolled"))
			         .collect(Collectors.toList()).size();
			        
			
			Integer lostCnt =enquiries.stream()
			 .filter(e -> e.getEnqStatus().equals("Lost"))
	         .collect(Collectors.toList()).size();     
			
			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
			
		}
		
		return response ;
	}*/
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
	    DashboardResponse response = new DashboardResponse();

	    Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
	    
	    if (findById.isPresent()) {
	        UserDtlsEntity userEntity = findById.get();

	        // Based on enquiry
	        List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
	        Integer totalCnt = enquiries.size();

	        Integer enrolledCnt = (int) enquiries.stream()
	                .filter(e -> "Enrolled".equals(e.getEnqStatus()))
	                .count();

	        Integer lostCnt = (int) enquiries.stream()
	                .filter(e -> "Lost".equals(e.getEnqStatus()))
	                .count();

	        response.setTotalEnquriesCnt(totalCnt);
	        response.setEnrolledCnt(enrolledCnt);
	        response.setLostCnt(lostCnt);
	    }

	    return response;
	}



	@Override
	public List<String> getCourses() {
		
		List<CourseEntity> findAll=coursesRepo.findAll();
		//i got all record in the table but i need only name for drop down
		
		//i adding all names in this collection
		List<String> names = new ArrayList<String>();
		
	    for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName()); //adding the name in this collection
		}
		// TODO Auto-generated method stub
		return names;
	}


	//@Override
	public List<String> getEnqStatuses() {
		
		List<EnqStatusEntity> findAll= statusRepo.findAll();
		
		List<String> statusList =new ArrayList<>();
		
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());   
		}
		// TODO Auto-generated method stub
		return statusList;
	}


	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		// TODO Auto-generated method stub
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId =(Integer)session.getAttribute(AppConstants.STR_USER_ID);
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		
		
		enqEntity.setUser(userEntity);
		enqRepo.save(enqEntity);
		return true;
	}


	/*@Override
	
     public List<StudentEnqEntity>getEnquiries(){
		Integer userId=(Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity>findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity=findById.get();
			List<StudentEnqEntity> enquiries =userDtlsEntity.getEnquiries();
			return enquiries;
		}
	     return getEnquiries(); 	
	}
	*/
	@Override
	public List<StudentEnqEntity> getEnquiries() {
	    Integer userId = (Integer) session.getAttribute("userId");
	    Optional  <UserDtlsEntity> findById = userDtlsRepo.findById(userId);
	    if (findById.isPresent()) {
	        UserDtlsEntity userDtlsEntity = findById.get();
	        return userDtlsEntity.getEnquiries();
	    }
	    
	    return Collections.emptyList(); // Return an empty list if user not found
	}
	/*
	 * @Override
	 * 
	 * public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria
	 * criteria,Integer userId){
	 * Optional<UserDtlsEntity>findById=userDtlsRepo.findById(userId);
	 * if(findById.isPresent()) { UserDtlsEntity userDtlsEntity=findById.get();
	 * List<StudentEnqEntity> enquiries =userDtlsEntity.getEnquiries();
	 * 
	 * //filter Logic if(null!=criteria.getCourseName()
	 * &!"".equals(criteria.getCourseName())) { enquiries = enquiries.stream()
	 * .filter(e -> e.getCourseName().equals(criteria.getCourseName()))
	 * .collect(Collectors.toList()); } if(null!=criteria.getEnqStatus()
	 * &"".equals(criteria.getEnqStatus())) { enquiries.stream() .filter(e ->
	 * e.getEnqStatus().equals(criteria.getEnqStatus()))
	 * .collect(Collectors.toList()); } if(null!=criteria.getClassMode()
	 * &!"".equals(criteria.getClassMode())) { enquiries = enquiries.stream()
	 * .filter(e -> e.getClassMode().equals(criteria.getClassMode()))
	 * .collect(Collectors.toList()); } return enquiries; } return null;
	 * 
	 * }
	 */
	  }
	
	


