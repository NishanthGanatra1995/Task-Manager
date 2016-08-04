package processor;

import threads.Threader;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by Nishanth on 07/07/2016.
 */
public class Process {

	public final String name;
	private Threader running;
	private ObservedProgress progressObserved;

	private Method method;

	public Process(String name, Threader running, ProcessManager processManager) {
		this.name = name;
		this.running = running;

//		boolean found = false;
//		for (int i = 0; i < running.getClass().getMethods().length; i++) {
//			if (running.getClass().getMethods()[i].getName().equals("setProgressObserved")) {
//				found = true;
//				method = running.getClass().getMethods()[i];
//			}
//		}
//
//		if (!found) System.err.println("Couldn't find progressObserved");

		progressObserved = new ObservedProgress();
		progressObserved.setProgress(0);
		progressObserved.addObserver(processManager);

		running.setProgressObserved(progressObserved);

//		try {
//			method.invoke(running, progressObserved);
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			e.printStackTrace();
//		}
	}

	public Callable<Object> getRunnable() {
		return running;
	}

	public Threader getThreader() {
		return running;
	}
}
