package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtil {
	public static void input(File file, String str, String mode) {

		int fileLen = fileLength(file);
		String fileOut = output(file);
		
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			
			if (mode.equals("new")) {
				br.write(str, 0, str.length());
				br.flush();
			} else if (mode.equals("old")) {
				str = fileOut + str;
				br.write(str, 0, str.length());
				br.flush();
			} else if (mode.equals("else")) {
				str = fileOut + "\n" + str + "\n";
				br.write(str, 0, str.length());
				br.flush();
			} else if (mode.equals("init")) {				
				br.flush();
			} else { // else와 같은 기능 다른 방법의 코딩
				br.write(fileOut, 0, fileLen);
				if (fileLen != 0) {
					br.newLine();
				}
				br.write(str, 0, str.length());
				br.flush();
			}
			
			br.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int fileLength(File file) {
		int b, count = 0;
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));
			while ((b = buffRead.read()) != -1) {
				count++;
			}
			buffRead.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return count;
	}

	public static String output(File file) {
		int b;
		String fileContent = "";
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));

			while ((b = buffRead.read()) != -1) {
				fileContent += (char) b;
			}

			buffRead.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return fileContent;
	}
}
