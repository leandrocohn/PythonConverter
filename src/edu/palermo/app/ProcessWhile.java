package edu.palermo.app;

import java.util.ArrayList;
import java.util.List;

import edu.palermo.exception.InstructionNotFoundException;
import edu.palermo.model.Instruction;
import edu.palermo.model.impl.ElseIfInstruction;
import edu.palermo.model.impl.IfInstruction;
import edu.palermo.model.impl.WhileInstruction;

public class ProcessWhile {

	private static List<Instruction> instructions;
	private static List<List<Instruction>> allInstructions;
	private static Integer order;
	private static List<String> condition = null;

	public static void processCode(String line, Integer orden) throws InstructionNotFoundException {
		if (line.contains("do when") || line.equals("do")) {
			init();
			if (line.contains("do when")) {
				condition.add(line.substring(line.indexOf("do when") + 7, line.length()));
			}
			order = orden;
		} else if (line.contains("when")) {
			if (instructions != null && instructions.size() > 0 && instructions.get(0) != null) {
				allInstructions.add(instructions);
			}
			instructions = new ArrayList<Instruction>();
			condition.add(line.substring(line.indexOf("when") + 4, line.length()));
		} else if (line.contains("done")) {
			allInstructions.add(instructions);
		} else {
			Instruction instruction = ParserApp.convertInstruction(line);
			if (instruction != null) {
				instructions.add(instruction);
			}
		}
	}

	private static void init() {
		condition = new ArrayList<String>();
		allInstructions = new ArrayList<List<Instruction>>();
		instructions = new ArrayList<Instruction>();
	}

	public static List<Instruction> getInstructions() {
		instructions = new ArrayList<Instruction>();
		String conditions =  "";
		for (String condi : condition) {
			conditions += condi + " OR ";
		}

		// A la condicion final, se le saca el último OR
		instructions.add(new WhileInstruction(conditions.substring(0, conditions.length() - 4), order));
		order++;
		instructions.add(new IfInstruction("if " + condition.get(0) + " then ", order));
		instructions.addAll(setOrder(allInstructions.get(0), order + 1));
		for (int indexGuarda = 1; indexGuarda < allInstructions.size(); indexGuarda ++) {
			instructions.add(new ElseIfInstruction("elseif " + condition.get(indexGuarda) + " then ", order));
			instructions.addAll(setOrder(allInstructions.get(indexGuarda), order + 1));
		}
		return instructions;
	}

	private static List<Instruction> setOrder(List<Instruction> instructions, int order) {
		List<Instruction> finalInstructions = new ArrayList<Instruction>();
		for(Instruction instruction: instructions) {
			instruction.setOrder(instruction.getOrder() + order);
			finalInstructions.add(instruction);
		}
		return finalInstructions;
	}

}
