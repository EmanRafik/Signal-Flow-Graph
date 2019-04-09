package package1;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class OutputController {

	@FXML
	private TextArea paths;
	@FXML
	private TextArea loops;
	@FXML
	private TextArea delta;
	@FXML
	private TextArea transferFunction;
	@FXML
	private TextArea extraPath;
	Graph g = Graph.getInstance();

	@FXML
	public void showGraph(ActionEvent event) {
		GraphGui gui = new GraphGui();
		try {
			gui.start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void getResults() {
		g.setPaths();
		g.loops();
		g.setDelta();
		delta.appendText("Delta = " + g.getDelta().getValue() + g.getDelta().getSymbol() + "\n");
		for (int i = 0; i < g.getPaths().size(); i++) {
			Path p = g.getPaths().get(i);
			paths.appendText("Path" + Integer.toString(i + 1) + ": (");
			for (int j = 0; j < p.getNodes().size(); j++) {
				paths.appendText(p.getNodes().get(j).toString());
				if (j < p.getNodes().size() - 1) {
					paths.appendText(",");
				} else {
					if (p.getGain().length() > 0) {
						if (p.getGainVal() == 1) {
							paths.appendText(")\t Gain = " + p.getGain() + "\n");
						} else if (p.getGainVal() == -1) {
							paths.appendText(")\t Gain = -" + p.getGain() + "\n");
						} else {
							paths.appendText(")\t Gain = " + p.getGainVal() + " " + p.getGain() + "\n");
						}
					} else {
						paths.appendText(")\t Gain = " + p.getGainVal() + "\n");
					}
				}
			}
			p.setDelta();
			delta.appendText("Delta" + Integer.toString(i + 1) + " = " + p.getDelta().getValue()
					+ p.getDelta().getSymbol() + "\n");
		}
		if (g.getLoops().size() > 0) {
			loops.appendText("Individual Loops:\n");
		}
		for (int i = 0; i < g.getLoops().size(); i++) {
			Loop l = g.getLoops().get(i);
			i++;
			loops.appendText("Loop" + Integer.toString(i) + ": (");
			i--;
			for (int j = 0; j < l.getNodes().size(); j++) {
				loops.appendText(l.getNodes().get(j).toString());
				if (j < l.getNodes().size() - 1) {
					loops.appendText(",");
				} else {
					if (l.getGain().length() > 0) {
						if (l.getGainVal() == 1) {
							loops.appendText(")\t Gain = " + l.getGain() + "\n");
						} else if (l.getGainVal() == -1) {
							loops.appendText(")\t Gain = -" + l.getGain() + "\n");
						} else {
							loops.appendText(")\t Gain = " + l.getGainVal() + " " + l.getGain() + "\n");
						}
					} else {
						loops.appendText(")\t Gain = " + l.getGainVal() + "\n");
					}
				}
			}
		}
		for (int i = 0; i < g.getNonTouchingLoops().size(); i++) {
			i++;
			loops.appendText("\n" + Integer.toString(i+1) + " non-touching loops:\n");
			i--;
			LinkedList<Loop> x = g.getNonTouchingLoops().get(i);
			for (int j = 0; j < x.size(); j++) {
				Loop y = x.get(j);
				for (int k = 0; k < y.getNonTouching().size(); k++) {
					Integer f = y.getNonTouching().get(k);
					f++;
					loops.appendText("L" + f);
					if (k < y.getNonTouching().size() - 1) {
						loops.appendText(", ");
					} else {
						loops.appendText("\n");
					}
				}
			}
		}
		transferFunction.appendText(g.transferFunction());

		@SuppressWarnings("rawtypes")
		LinkedList<javafx.util.Pair> rPaths = g.getReqP();
		for (int k = 0; k < rPaths.size(); k++) {
			int start = (int) g.getReqP().get(k).getKey();
			int end = (int) g.getReqP().get(k).getValue();
			g.setPath(new LinkedList<Integer>());
			g.setGainSympol(new LinkedList<String>());
			g.setGainValue(new LinkedList<Float>());
			g.setTemp(new LinkedList<Path>());
			LinkedList<Path> ePaths = (LinkedList<Path>) g.path(start, end).clone();
			if (ePaths.size() == 0) {
				extraPath.appendText("There is NO ");
			}
			extraPath.appendText("Paths from " + start + "to" + end + "\n");
			for (int i = 0; i < ePaths.size(); i++) {
				Path p = ePaths.get(i);
				extraPath.appendText("Path" + Integer.toString(i + 1) + ": (");
				for (int j = 0; j < p.getNodes().size(); j++) {
					extraPath.appendText(p.getNodes().get(j).toString());
					if (j < p.getNodes().size() - 1) {
						extraPath.appendText(",");
					} else {
						if (p.getGain().length() > 0) {
							if (p.getGainVal() == 1) {
								extraPath.appendText(")\t Gain = " + p.getGain() + "\n");
							} else if (p.getGainVal() == -1) {
								extraPath.appendText(")\t Gain = -" + p.getGain() + "\n");
							} else {
								extraPath.appendText(")\t Gain = " + p.getGainVal() + " " + p.getGain() + "\n");
							}
						} else {
							extraPath.appendText(")\t Gain = " + p.getGainVal() + "\n");
						}

					}
				}
			}
			g.setPathsb(new LinkedList<Path>());
			g.setPathb(new LinkedList<Integer>());
			g.setGainValueb(new LinkedList<Float>());
			g.setGainSympolb(new LinkedList<String>());
			g.backwardPaths(start, end);
			ePaths = g.getPathsb();
			if (ePaths.size() == 0) {
				extraPath.appendText("There is NO ");
			}
			extraPath.appendText("Paths from " + end + "to" + start + "\n");
			for (int i = 0; i < ePaths.size(); i++) {
				Path p = ePaths.get(i);
				extraPath.appendText("Path" + Integer.toString(i + 1) + ": (");
				for (int j = 0; j < p.getNodes().size(); j++) {
					extraPath.appendText(p.getNodes().get(j).toString());
					if (j < p.getNodes().size() - 1) {
						extraPath.appendText(",");
					} else {
						if (p.getGain().length() > 0) {
							if (p.getGainVal() == 1) {
								extraPath.appendText(")\t Gain = " + p.getGain() + "\n");
							} else if (p.getGainVal() == -1) {
								extraPath.appendText(")\t Gain = -" + p.getGain() + "\n");
							} else {
								extraPath.appendText(")\t Gain = " + p.getGainVal() + " " + p.getGain() + "\n");
							}
						} else {
							extraPath.appendText(")\t Gain = " + p.getGainVal() + "\n");
						}
					}
				}
			}
		}
	}
}
