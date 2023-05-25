package in.rahul.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rahul.binding.LoginForm;
import in.rahul.binding.SignUpForm;
import in.rahul.binding.UnlockForm;
import in.rahul.constants.AppConstants;
import in.rahul.entity.UserDtlsEntity;
import in.rahul.repo.UserDtlsRepo;
import in.rahul.util.EmailUtils;
import in.rahul.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDtlsRepo UserDtlsRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = UserDtlsRepo.findByEmail(form.getEmail());
		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(AppConstants.STR_UNLOCKED); 
			UserDtlsRepo.save(entity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean signup(SignUpForm form) {
    //when i signning in project first check the email is present or not
		//create a user dtls entity  
		UserDtlsEntity user = UserDtlsRepo.findByEmail(form.getEmail());
	 //if record is  match with data then tell choose unique  email
		if (user != null) {

			return false; //when this false is execute then all logics are not execute
		}
		
		//TODO : copy data binding onj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		String tempPwd = PwdUtils.generateRandompwd();
		entity.setPwd(tempPwd);
		entity.setAccStatus(AppConstants.STR_LOCKED);
		UserDtlsRepo.save(entity);
		String to = form.getEmail();
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use Below Password To Unlock Your Account</h1>");
		body.append("Temporory pwd :" + tempPwd);
		body.append("<br/>");
		body.append("<br/>");

		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return false;
	}

	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = UserDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if (entity == null) {
			return AppConstants.INVALID_CREDETIAL_MSG;
		}
		if (entity.getAccStatus().equals(AppConstants.STR_LOCKED )) {
			return AppConstants.STR_ACCOUNT_LOCK_MSG;
		}

		// create session management and store user data in session
		session.setAttribute(AppConstants.STR_USER_ID , entity.getUserId());

		return AppConstants.STR_SUCCESS;
	}

	@Override
	public boolean forgotPwd(String email) {
		UserDtlsEntity entity = UserDtlsRepo.findByEmail(email);
		if (entity == null) {
			return false;
		}
		String Subject = AppConstants.RECOVER_PWD_EMAIL_SUBJECT;
		String Body = "Your Pwd :: " + entity.getPwd();

		emailUtils.sendEmail(email, Subject, Body);
		return true;
	}

}