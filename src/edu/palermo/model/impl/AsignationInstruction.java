package edu.palermo.model.impl;

import edu.palermo.model.Instruction;

public class AsignationInstruction extends Instruction {

	public AsignationInstruction(String code, Integer order) {
		super(code, order);
	}

	@Override
	protected String convertToPython() {
		return code.replaceAll(":=", "=");
	}
}
