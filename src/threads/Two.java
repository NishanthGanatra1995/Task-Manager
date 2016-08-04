package threads;

/**
 * Created by Nishanth on 20/07/2016.
 */
public class Two extends Threader {

	public Two() {
		moreThanOne = true;
	}

	@Override
	public Object call() throws Exception {
		try {
			for (int i = 0; i < 10; i++) {
				progressObserved.setProgress(i*10);
				Thread.sleep(800);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			progressObserved.setProgress(100);

		}
		progressObserved.setProgress(100);
		return -1;
	}

}
