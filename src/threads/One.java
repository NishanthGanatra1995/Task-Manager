package threads;

import java.util.Random;

/**
 * Created by Nishanth on 20/07/2016.
 */
public class One extends Threader {

	public One () {
		moreThanOne = true;
	}

	@Override
	public Object call() throws Exception {
		Random random = new Random();
		try {
			for (int i = 0; i < 10; i++) {
				if (!Thread.interrupted()) {
					if (i==5) throw new Exception();
					progressObserved.setProgress(i * 10);
					Thread.sleep(400);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			progressObserved.setProgress(100);
		}
		progressObserved.setProgress(100);
		return -1;
	}
}
