package sample;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import processor.Process;
import processor.ProcessManager;
import saving.DataToSave;
import saving.FileSerialiser;
import threads.*;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {

	public ListView gridPane;
	public SplitPane splitPlane;
	public Button expandTasks;
	public TextField textField;
	public TextField aaSave;
	public TextField bbSave;
	public Slider ccSave;

	private ProcessManager processManager;
	private DataToSave dataToSave;

	public Controller() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		splitPlane.widthProperty().addListener((observable, oldValue, newValue) -> {resizeTasks();});
		processManager = new ProcessManager();

		processManager.addObserver(this);

	}

	public void addProcess(String name, Threader runnable) {
		if (!processManager.checkCanRun(name, runnable)) return;

		AnchorPane anchorPane = new AnchorPane();

		Label label = new Label(name);
		label.setLayoutX(5);
		label.setLayoutY(5);
		anchorPane.getChildren().add(label);

		Button button = new Button("Force Stop");
		button.setOnAction(event -> {
			processManager.interrupt(name);
		});
		button.setLayoutY(20);

		ProgressBar progressBar = new ProgressBar(0.0);
		progressBar.setLayoutX(5);
		progressBar.setLayoutY(25);

		progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
			if (((double)newValue - (double)oldValue) > 0.05) {
				for (double i = (double) oldValue; i < (double)newValue; i = i + 0.001) {
					progressBar.setProgress(i);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						progressBar.setProgress(100);
						processManager.interrupt(name);
						e.printStackTrace();
						break;
					}
				}
			}
			progressBar.setProgress((double) newValue);
		});

		Process process = new Process(name, runnable, processManager);
		processManager.addProcess(process, progressBar);

		anchorPane.getChildren().add(progressBar);
		anchorPane.getChildren().addAll(button);

		resizeTask(button, progressBar);

		progressBar.getStyleClass().add("progress-bara");

		gridPane.getItems().add(anchorPane);

		gridPane.scrollTo(gridPane.getItems().size()-1);

		FadeTransition ft = new FadeTransition(Duration.millis(500), anchorPane);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();

		ScaleTransition st = new ScaleTransition(Duration.millis(250), anchorPane);
		st.setFromX(0.9f);
		st.setFromY(0.9f);
		st.setToX(1);
		st.setToY(1);
		st.play();
	}

	private void resizeTask(Button button, ProgressBar progressBar) {
		progressBar.setPrefWidth(gridPane.getWidth() - expandTasks.getWidth()*5 - 20);
		button.setLayoutX(gridPane.getWidth() - expandTasks.getWidth()*5);
	}

	public void resizeTasks() {
		for (int i = 0; i < gridPane.getItems().size(); i++) {
			AnchorPane anchorPane = (AnchorPane) gridPane.getItems().get(i);
			ProgressBar progressBar = (ProgressBar) anchorPane.getChildren().get(1);
			Button button = (Button) anchorPane.getChildren().get(2);
			resizeTask(button, progressBar);
		}

	}

	public void startProcessOne(ActionEvent actionEvent) {
		addProcess("One", new One());
	}

	public void startProcessTwo(ActionEvent actionEvent) {
		addProcess("Two", new Two());
	}

	public void startProcessThree(ActionEvent actionEvent) {
		addProcess("Three", new Three());
	}

	private void cleanUpFinishedProccessessGUI() {
		for (int i = gridPane.getItems().size()-1; i >= 0; i--) {
			AnchorPane temp = (AnchorPane) gridPane.getItems().get(i);
			ProgressBar progressBar = (ProgressBar) temp.getChildren().get(1);

			if (progressBar.getProgress() >= 1.0) {
				FadeTransition ft = new FadeTransition(Duration.millis(500), (AnchorPane) gridPane.getItems().get(i));
				ft.setFromValue(1.0);
				ft.setToValue(0.0);
				ft.play();

				ScaleTransition st = new ScaleTransition(Duration.millis(250), (AnchorPane) gridPane.getItems().get(i));
				st.setFromX(1);
				st.setFromY(1);
				st.setToX(0.9f);
				st.setToY(0.9f);
				st.play();

				final int finalI = i;
				ft.setOnFinished(event -> gridPane.getItems().remove(finalI));
			}
		}
	}

	public void clear(ActionEvent actionEvent) {
		processManager.cleanUp();
		cleanUpFinishedProccessessGUI();
	}

	public void saveFile(ActionEvent actionEvent) {
		DataToSave dataToSave = new DataToSave();
		dataToSave.setAa(aaSave.getText());
		dataToSave.setBb(bbSave.getText());
		dataToSave.setValue(ccSave.getValue());
		if (FileSerialiser.checkFileNameFree(textField.getText() + ".ser")) {
			addProcess("Save", new WriteFile(textField.getText()+".ser", dataToSave));
		}
	}

	public void clearData(ActionEvent actionEvent) {
		aaSave.setText("");
		bbSave.setText("");
		ccSave.setValue(0);
	}

	public void loadData(ActionEvent actionEvent) {
		if (!FileSerialiser.checkFileNameFree(textField.getText()+".ser")) {
			addProcess("Load", new ReadFile(textField.getText()+".ser"));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!arg.getClass().toString().equals("class java.lang.Integer")) {
			Platform.runLater(() -> {
				aaSave.setText(((DataToSave) arg).getAa());
				bbSave.setText(((DataToSave) arg).getBb());
				ccSave.setValue(((DataToSave) arg).getValue());
			});
		}
	}
}
