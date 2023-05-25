package in.rahul.service;

import java.util.List;

import in.rahul.binding.DashboardResponse;
import in.rahul.binding.EnquiryForm;
import in.rahul.binding.EnquirySearchCriteria;
import in.rahul.entity.StudentEnqEntity;
public interface EnquiryService {
	
  public DashboardResponse getDashboardData(Integer userId);
  
  
    public List<String>getCourses();

    public List<String>getEnqStatuses();
    
    public boolean saveEnquriry(EnquiryForm form);


	public List<StudentEnqEntity> getEnquiries();
	
	//public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria , Integer userId);
	
}
  
  
  
  
  
  
  
  
  
  
  
  //public String upsertEnquiry(EnquiryForm form);
  //public List<EnquiryForm>getEnquries(Integer userId,EnquirySearchCriteria criteria);
  
 // public EnquiryForm getEnquiry(Integer enqId);

