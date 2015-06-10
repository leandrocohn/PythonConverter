package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class WhileInstruction extends Instruction {

	public WhileInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return "while " + super.convertConditions(code + ":");
	}

}
