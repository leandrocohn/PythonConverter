package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class ElseIfInstruction extends Instruction {

	public ElseIfInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return super.convertConditions(code.replaceAll("elseif", "elif").replace(" then", ":"));
	}
}
