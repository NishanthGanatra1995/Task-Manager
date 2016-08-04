package threads;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by Nishanth on 27/07/2016.
 */

public class ReadFile extends Threader {

	private final String fileName;

	public ReadFile (String fileName) {
		this.fileName = fileName;
		moreThanOne = false;
	}

	@Override
	public Object call() throws Exception {
		Object temp = null;
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			ObjectInputStream objStream = new ObjectInputStream(inputStream);
			temp = objStream.readObject();
			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			progressObserved.setProgress(100);
		}
		progressObserved.setProgress(100);
		return temp;
	}
}