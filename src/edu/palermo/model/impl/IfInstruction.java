package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class IfInstruction extends Instruction {

	public IfInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return super.convertConditions(code.replaceAll(" then", ":").replaceAll("\\s+", " "));
	}

}
