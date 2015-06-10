package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class FunctionInstruction extends Instruction {

	public FunctionInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return code.replaceAll("function", "def").replaceAll("\\s+", " ") + ":";
	}

}
