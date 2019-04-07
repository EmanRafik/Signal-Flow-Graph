package package1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class OutputController {
	
	@FXML
	private TextArea paths;
	@FXML
	private TextArea loops;
	@FXML
	private TextArea delta;
	@FXML
	private TextArea transferFunction;
	Graph g = Graph.getInstance();
	
	@FXML
	public void showGraph(ActionEvent event) {
		SceneController controller = SceneController.getInstance();
		controller.showGraph();
	}
	
	@FXML
	public void getResults() {
		g.setPaths();
		g.loops();
		g.setDelta();
		delta.appendText("Delta = " + g.getDelta().getValue() + g.getDelta().getSymbol() + "\n");
		for (int i = 0; i < g.getPaths().size(); i++) {
			Path p = g.getPaths().get(i);
			paths.appendText("Path" + Integer.toString(i+1) + ": (");
			for (int j = 0; j < p.getNodes().size(); j++) {
				paths.appendText(p.getNodes().get(j).toString());
				if (j < p.getNodes().size() - 1) {
					paths.appendText(",");
				} else {
					paths.appendText(")\t Gain = " + p.getGainVal() + " " + p.getGain() + "\n");
				}
			}
			p.setDelta();
			delta.appendText("Delta" + Integer.toString(i+1) + " = " + p.getDelta().getValue() + p.getDelta().getSymbol()+ "\n");
		}
		if (g.getLoops().size() > 0) {
			loops.appendText("Individual Loops:\n");
		}
		for (int i = 0; i < g.getLoops().size(); i++) {
			Loop l = g.getLoops().get(i);
			loops.appendText("Loop" + Integer.toString(i+1) + ": (");
			for (int j = 0; j < l.getNodes().size(); j++) {
				loops.appendText(l.getNodes().get(j).toString());
				if (j < l.getNodes().size() - 1) {
					loops.appendText(",");
				} else {
					loops.appendText(")\t Gain = " + l.getGainVal() + " " + l.getGain() + "\n");
				}
			}
		}
		transferFunction.appendText(g.transferFunction());
	}
}
