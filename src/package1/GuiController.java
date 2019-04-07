package package1;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class GuiController {

	@FXML
	private Button enterNoOfNodes;
	@FXML
	private Button enterEdge;
	@FXML
	private Button s;
	@FXML
	private Button finish;
	@FXML
	private TextField noOfNodes;
	@FXML
	private TextField startEdge;
	@FXML
	private TextField endEdge;
	@FXML
	private TextField gain;
	@FXML
	private TextArea paths;
	@FXML
	private TextArea loops;
	@FXML
	private TextArea delta;
	@FXML
	private TextArea transferFunction;
	private int n;
	private int[][] graph;
	private String[][] Gain;
	Graph g = Graph.getInstance();

	@FXML
	public void enterN(ActionEvent event) {
		n = Integer.parseInt(noOfNodes.getText());
		graph = new int[n][n];
		Gain = new String[n][n];
		enterEdge.setDisable(false);
		finish.setDisable(false);
		enterNoOfNodes.setDisable(true);
	}

	@FXML
	public void enterEdge(ActionEvent event) {
		int start = Integer.parseInt(startEdge.getText());
		int end = Integer.parseInt(endEdge.getText());
		graph[start-1][end-1] = 1;
		Gain[start-1][end-1] = gain.getText();
		startEdge.clear();
		endEdge.clear();
		gain.clear();
	}

	@FXML
	public void finish(ActionEvent event) {
		g.setN(n);
		g.setGain(Gain);
		g.setGraph(graph);
		SceneController controller = SceneController.getInstance();
		try {
			controller.activate(FXMLLoader.load(getClass().getResource("/package1/Output.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
