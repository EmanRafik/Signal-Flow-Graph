package package1;

import java.util.LinkedList;

public class Path {

	private LinkedList<Integer> nodes;
	private String gain = "";
	private float gainVal = 1;
	private Delta delta;
	private LinkedList<Loop> loops = new LinkedList<Loop>();
	
	public LinkedList<Integer> getNodes() {
		return nodes;
	}

	public void setNodes(LinkedList<Integer> nodes) {
		this.nodes = nodes;
	}

	public String getGain() {
		return gain;
	}

	public void setGain(String gain) {
		this.gain = gain;
	}

	public float getGainVal() {
		return gainVal;
	}

	public void setGainVal(float gainVal) {
		this.gainVal = gainVal;
	}

	public void setDelta() {
		getLoops();
		delta = new Delta(loops);
		delta.Value();
	}

	public Delta getDelta() {
		return delta;
	}

	private void getLoops() {
		Graph g = Graph.getInstance();
		loops.clear();
		for (int i = 0; i < g.getLoops().size(); i++) {
			boolean flag = true;
			Loop x = g.getLoops().get(i);
			for (int j = 0; j < x.getNodes().size(); j++) {
				for (int k = 0; k < nodes.size(); k++) {
					if (x.getNodes().get(j).equals(nodes.get(k))) {
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				loops.add(x);
			}
		}
	}
}
