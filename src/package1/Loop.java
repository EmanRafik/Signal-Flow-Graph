package package1;

import java.util.ArrayList;
import java.util.LinkedList;

public class Loop {

	private LinkedList<Integer> nodes = new LinkedList<Integer>();
	private String gain = "";
	private float gainVal = 1;
	private ArrayList<Integer> nonTouching = new ArrayList<Integer>();

	public ArrayList<Integer> getNonTouching() {
		return nonTouching;
	}

	public void setNonTouching(ArrayList<Integer> nonTouching) {
		this.nonTouching = nonTouching;
	}

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

}
