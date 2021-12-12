package org.bz.nisum.ms.app.usuarios.errors;

public class PatternPasswordException extends RuntimeException {
	
	public PatternPasswordException(String message) {
		super("The password does not meet the required pattern. "
				.concat(message));
	}
	
	private static final long serialVersionUID = -43792510933436020L;

}
