package edu.palermo.exception;

public class InstructionNotFoundException extends Exception {

	public InstructionNotFoundException(String line) {
		super("The instruction " + line + " was not defined");
	}

	private static final long serialVersionUID = 1L;

}
