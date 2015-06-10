package edu.palermo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import edu.palermo.model.Instruction;

public class FileManager {

	public static final String PATH_OUTPUT_PY_FILE = "output.py";
	private static List<String> listaInstrucciones = null;
	private static int index = -1;

	public static void readFile(String path) {
		FileInputStream fis = null;
		BufferedReader reader = null;
		listaInstrucciones = new ArrayList<String>();

		try {
			fis = new FileInputStream(path);
			reader = new BufferedReader(new InputStreamReader(fis));

			System.out.println("Leyendo el archivo: " + path);

			String line = reader.readLine();
			while (line != null) {
				listaInstrucciones.add(line.replaceAll("^\\s+", ""));
				line = reader.readLine();
			}

		} catch (FileNotFoundException ex) {
			System.out.println("No se encuentra el archivo");
		} catch (IOException ex) {
			System.out.println("Excepcion de IO");
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				System.out.println("Excepcion de IO");
			}
		}
	}

	public static String pullLine() {
		return listaInstrucciones.size() > ++index ? listaInstrucciones.get(index) : null;
	}

	public static boolean writePyFile(List<Instruction> instructionList) {

		File fout = new File(PATH_OUTPUT_PY_FILE);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fout);

			OutputStreamWriter osw = new OutputStreamWriter(fos);

			for (Instruction instruction : instructionList) {
				osw.write(instruction.getCodePy() + "\n");
			}

			osw.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
