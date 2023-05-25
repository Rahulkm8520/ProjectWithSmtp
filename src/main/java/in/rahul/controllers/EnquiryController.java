package in.rahul.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.rahul.binding.DashboardResponse;
import in.rahul.binding.EnquiryForm;
import in.rahul.binding.EnquirySearchCriteria;
import in.rahul.entity.StudentEnqEntity;
import in.rahul.repo.StudentEnqRepo;
import in.rahul.service.EnquiryService;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;
	@Autowired
	private StudentEnqRepo repo;
	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();

		return "index";
	}
	@GetMapping("/edit")
	public String edit(@RequestParam("enqId") Integer enqId, @ModelAttribute("enquiry") StudentEnqEntity enquiry, Model model) {
		Optional<StudentEnqEntity> findById = repo.findById(enqId);
		if (findById.isPresent()) {
			StudentEnqEntity edit = findById.get();
			edit.setCourseName(enquiry.getCourseName()); 
		    edit.setStudentNames(enquiry.getStudentNames());
		    edit.setClassMode(enquiry.getClassMode());
		    edit.setEnqStatus(enquiry.getEnqStatus());
		    edit.setStudentPhno(enquiry.getStudentPhno());
			// Update other fields as necessary
			repo.save(edit);
			model.addAttribute("successMsg", "Enquiry updated successfully");
		} else {
			model.addAttribute("errorMsg", "Enquiry not found");
		}
		return "add-enquiry";
	}

	@GetMapping("/dashboard")
	public String dashBoardPage(Model model) {
		//when login is success  am i storing user ID  //use type casting
		Integer userId = (Integer) session.getAttribute("userId");
		//based on userId call service layer 
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		//dashboard object send to UI using Model attribute
		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		boolean status = enqService.saveEnquriry(formObj);
		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		List<String> courses = enqService.getCourses();
		List<String> enqStatuses = enqService.getEnqStatuses();
		EnquiryForm formObj = new EnquiryForm();
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	private void initForm(Model model) {
		// get courses for drop down
		List<String> courses = enqService.getCourses();
		// get eng status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		// creaate binding class object
		EnquiryForm formObj = new EnquiryForm();

		// Set data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
	}
//enquiries method will get all enquery from users 
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnqEntity> enquries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquries);

		return "view-enquiries";
	}
	
	//filter enqueries will gate the data based on filter
	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam  String cname ,@RequestParam String status, @RequestParam String mode ,Model model) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);
		
		System.out.println(criteria);
	
		/*
		 * Integer userId = (Integer) session.getAttribute("userId");
		 * List<StudentEnqEntity> filteredEnqs=enqService.getFilteredEnqs(criteria,
		 * userId);
		 * 
		 * model.addAttribute("enquiries", filteredEnqs);
		 */
		  
		return "view-enquiries";
	}
	
	
}
