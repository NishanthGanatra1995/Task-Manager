package processor;

import javafx.scene.control.ProgressBar;
import threads.Threader;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Nishanth on 07/07/2016.
 */
public class ProcessManager extends Observable implements Observer {

	private ArrayList<Triplet> processes;

	public ProcessManager() {
		processes = new ArrayList<>();
	}

	public boolean addProcess(Process process, ProgressBar progressBar) {
		//create new thread and add to list
		FutureTask<Object> futureTask = new FutureTask<>(process.getRunnable());
		Thread thread = new Thread(futureTask);
		progressBar.setProgress(0.0);
		processes.add(new Triplet(thread, process, progressBar));
		thread.start();
		new Thread(() -> {
			try {
				Object result = futureTask.get();
				setChanged();
				notifyObservers(result);
				cleanUp();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}).start();
		return true;
	}

	public void interrupt(String name) {
		for (int i = processes.size() - 1; i >= 0; i--) {
			if (((Process) processes.get(i).getB()).name.equals(name)) {
			//stop this process
				if (((Thread) processes.get(i).getA()).isAlive()) {
					//stop
					((Thread) processes.get(i).getA()).interrupt();
					((ProgressBar) processes.get(i).getC()).getStyleClass().removeAll();
					((ProgressBar) processes.get(i).getC()).getStyleClass().add("progress-bar-red");
				}
			}
		}
	}

	public void cleanUp() {
		for (int i = processes.size() - 1; i >= 0; i--) {
			if (!((Thread) processes.get(i).getA()).isAlive() || ((ProgressBar) processes.get(i).getC()).getProgress() > 100 || ((Thread) processes.get(i).getA()).isInterrupted()) {
				processes.remove(i);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		for (int i = 0; i < processes.size(); i++) {
			System.out.println(((Thread)processes.get(i).getA()).isAlive() + " " + ((ProgressBar) processes.get(i).getC()).getProgress());
		}

		ObservedProgress temp = ((ObservedProgress) arg);

		float tempProgress = temp.getProgress();
		Callable<Object> tempRunnable = temp.getRunnable();

		for (int i = 0; i < processes.size(); i++) {
			if (((Process) processes.get(i).getB()).getRunnable().equals(tempRunnable)) {
				((ProgressBar) processes.get(i).getC()).setProgress(tempProgress/100);
			}

		}

		cleanUp();
	}

	public void setAutoRemove(boolean autoRemove) {
	}

	public boolean checkCanRun(String name, Threader running) {
		for (int i = 0; i < processes.size(); i++) {
			if (((Process) processes.get(i).getB()).name.equals(name)) {
				if (!running.moreThanOne)
					return false;
			}
		}
		return true;
	}
}
