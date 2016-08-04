package threads;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Nishanth on 27/07/2016.
 */
public class WriteFile extends Threader {

	private final Object objectToSave;
	private final String fileName;

	public WriteFile (String fileName, Object objectToSave) {
		this.fileName = fileName;
		this.objectToSave = objectToSave;
		moreThanOne = false;
	}

	@Override
	public Object call() throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToSave);
			oos.close();
		} catch (Exception e) {
			progressObserved.setProgress(100);
			e.printStackTrace();
		}
		progressObserved.setProgress(100);
		return -1;
	}
}
