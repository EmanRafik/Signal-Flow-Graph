package package1;

import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

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
	private TextField firstN;
	@FXML
	private TextField secN;
	@FXML
	private Button enterPath;
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
	@SuppressWarnings("rawtypes")
	private LinkedList<Pair> extra = new LinkedList<Pair>();
	Graph g = Graph.getInstance();

	@FXML
	public void enterN(ActionEvent event) {
		n = Integer.parseInt(noOfNodes.getText());
		graph = new int[n][n];
		Gain = new String[n][n];
		enterEdge.setDisable(false);
		finish.setDisable(false);
		enterPath.setDisable(false);
		enterNoOfNodes.setDisable(true);
	}

	@FXML
	public void enterEdge(ActionEvent event) {
		int start = Integer.parseInt(startEdge.getText());
		int end = Integer.parseInt(endEdge.getText());
		graph[start][end] = 1;
		Gain[start][end] = gain.getText();
		startEdge.clear();
		endEdge.clear();
		gain.clear();
	}

	@FXML
	public void enterP(ActionEvent event) {
		int first = Integer.parseInt(firstN.getText());
		int second = Integer.parseInt(secN.getText());
		Pair<Integer, Integer> k = new Pair<Integer, Integer> (first,second);
		extra.add(k);
		firstN.clear();
		secN.clear();
	}
	@FXML
	public void finish(ActionEvent event) {
		g.setN(n);
		g.setGain(Gain);
		g.setGraph(graph);
		g.setReqP(extra);
		SceneController controller = SceneController.getInstance();
		try {
			controller.activate(FXMLLoader.load(getClass().getResource("/package1/Output.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
