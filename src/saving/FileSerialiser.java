package saving;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nishanth on 27/07/2016.
 */
public class FileSerialiser {

	private static Object temp;

	/**
	 * @return true if valid filename; must end in ser
	 */
	public static boolean checkFileNameFree (String nameToCheck) {
		Path path = Paths.get(nameToCheck);
		return !Files.exists(path);
	}

	public static Object readFile(String fileName) throws IOException, ClassNotFoundException, InterruptedException {
		FileInputStream inputStream = new FileInputStream(fileName);
		ObjectInputStream objStream = new ObjectInputStream(inputStream);
		Thread.sleep(5000);
		temp = objStream.readObject();
		objStream.close();
		return temp;
	}

	public static void writeFile (String fileName, Object objectToSave) throws IOException, InterruptedException {
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Thread.sleep(5000);
		oos.writeObject(objectToSave);
		oos.close();
	}

}
