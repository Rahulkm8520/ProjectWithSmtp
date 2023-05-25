package in.rahul.service;

import in.rahul.binding.LoginForm;
import in.rahul.binding.SignUpForm;
import in.rahul.binding.UnlockForm;

public interface UserService {
	
	public boolean signup(SignUpForm form);
	public boolean unlockAccount(UnlockForm form); 
	
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);

}
