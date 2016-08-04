package threads;

import processor.ObservedProgress;

import java.util.concurrent.Callable;

/**
 * Created by Nishanth on 21/07/2016.
 */
public abstract class Threader implements Callable<Object> {

	public ObservedProgress progressObserved;
	public boolean moreThanOne;

	public void setProgressObserved (ObservedProgress progressObserved) {
		this.progressObserved = progressObserved;
		progressObserved.setRunnable(this);
	}
}
