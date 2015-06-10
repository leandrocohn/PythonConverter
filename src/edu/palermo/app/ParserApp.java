package edu.palermo.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.palermo.exception.InstructionNotFoundException;
import edu.palermo.model.Instruction;
import edu.palermo.model.impl.*;
import edu.palermo.utils.FileManager;

public class ParserApp {

	private static Integer order = 0;

	public static void main(String[] args) {

		// Validacion de ruta
		if (args.length != 1) {
			System.out.println("Hay que informar en el primer parametro la ruta de donde tomar el archivo.");
			return;
		}

		try {
			FileManager.readFile(args[0]);

			List<Instruction> instructionList = new ArrayList<Instruction>();
			boolean insideWhile = false;

			String line = FileManager.pullLine();
			do {
				if (insideWhile) {
					if (line.contains("done")) {
						ProcessWhile.processCode(line, getOrder());
						instructionList.addAll(ProcessWhile.getInstructions());
						insideWhile = false;
					} else {
						ProcessWhile.processCode(line, getOrder());
					}
				} else {
					if (line.contains("do")) {
						insideWhile = true;
						ProcessWhile.processCode(line, getOrder());
					} else {
						// Proceso una instrucción que no sea do while
						Instruction instruction = convertInstruction(line);
						if (instruction != null) {
							instructionList.add(instruction);
						}
					}
				}
				line = FileManager.pullLine();
			} while (line != null);

			// Imprimo
			System.out.println("---------------- Archivo generado: ----------------");
			for (Instruction instruction : instructionList) {
				System.out.println(instruction.getCodePy());
			}

			// Creo el archivo Py
			FileManager.writePyFile(instructionList);

			// Ejecuta archivo Python
			Runtime.getRuntime().exec("python "+ FileManager.PATH_OUTPUT_PY_FILE);
		} catch (InstructionNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al intentar obtener el archivo para ejecutar Python");
		}
	}

	public static Instruction convertInstruction(String line)
			throws InstructionNotFoundException {

		if (line == null) {
			return null;
		}

		if (line.contains("elseif")) {
			decrementOrder();
			Instruction instruc = new ElseIfInstruction(line, getOrder());
			incrementOrder();
			return instruc;
		} else if (line.contains("if")) {
			Instruction instruc = new IfInstruction(line, getOrder());
			incrementOrder();
			return instruc;
		} else if (line.contains(":=")) {
			return new AsignationInstruction(line, getOrder());
		} else if (line.contains("fi") || line.contains("done") || line.contains("end function")) {
			decrementOrder();
			return null;
		} else if (line.contains("print") || line.contains("return")) {
			return new SimpleInstruction(line, getOrder());
		} else if (line.contains("function")) {
			Instruction instruc = new FunctionInstruction(line, getOrder());
			incrementOrder();
			return instruc;
		} else {
			throw new InstructionNotFoundException(line);
		}

	}

	public static Integer getOrder() {
		return order;
	}

	public static void incrementOrder() {
		order++;
	}

	public static void decrementOrder() {
		order--;
	}
}
