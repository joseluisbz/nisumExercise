package org.bz.nisum.ms.app.usuarios.errors;

public class ExistingMailException extends RuntimeException {
	
	public ExistingMailException(String mail) {
		super("The email: '"
				.concat(mail)
				.concat("' is already registered."));
	}
	
	private static final long serialVersionUID = -7241642475224506375L;

}
