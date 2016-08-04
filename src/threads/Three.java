package threads;

/**
 * Created by Nishanth on 20/07/2016.
 */
public class Three extends Threader {

	public Three() {
		moreThanOne = true;
	}

	@Override
	public Object call() throws Exception {
		try {
			for (int i = 0; i < 10; i++) {
				progressObserved.setProgress(i*10);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			progressObserved.setProgress(100);
		}
		progressObserved.setProgress(100);
		return -1;
	}

}
