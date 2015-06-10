package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class SimpleInstruction extends Instruction {

	public SimpleInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return code;
	}

}
