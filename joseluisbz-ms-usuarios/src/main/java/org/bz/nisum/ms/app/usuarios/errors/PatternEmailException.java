package org.bz.nisum.ms.app.usuarios.errors;

public class PatternEmailException extends RuntimeException {

	public PatternEmailException(String message) {
		super("The Email does not meet the required pattern. "
				.concat(message));
	}

	private static final long serialVersionUID = -4753386595346116814L;

}
