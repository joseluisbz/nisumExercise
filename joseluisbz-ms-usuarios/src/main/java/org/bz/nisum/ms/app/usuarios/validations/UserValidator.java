 package org.bz.nisum.ms.app.usuarios.validations;

import org.bz.nisum.ms.app.usuarios.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
	
	@Value("${format.password}")
	private String regexp;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if (!user.getPassword().matches(regexp)) {
			errors.rejectValue("password", "badpattern.user.password", "Pattern expected: " + regexp + ".");
		}
	}

}
