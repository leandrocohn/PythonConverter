package edu.palermo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Instruction {
	protected String code;
	private String codePy;
	private Integer order;

	protected Instruction(String code, Integer order) {
		this.code = code;
		this.order = order;
		this.codePy = convertToPython();
	}

	/**
	 * Método que se encarga de convertir a Python, implementado por
	 * su instrucción concreta.
	 *
	 */
	abstract protected String convertToPython();

	protected String convertConditions(String condition) {

		String patternString = ".*[\\w+|\\d+|\\s+|+|/|*|%]=[\\w+|\\d+|\\s+|+|/|*|%].*";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(condition);
		if (matcher.matches()) {
			condition = condition.replaceAll("=", "==");
		}
		return condition.toLowerCase();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodePy() {
		return getTabs() + codePy;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	private String getTabs() {
		String salida = "";
		for (int cant_Tabulaciones = 0; cant_Tabulaciones < order; cant_Tabulaciones++) {
			salida += "	";
		}

		return salida;
	}
}
