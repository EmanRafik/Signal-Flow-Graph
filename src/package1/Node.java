package package1;

import java.util.LinkedList;

public class Node {

	private LinkedList<String> gainS; // symbol
	private LinkedList<Integer> gainV; // value
	private int num;

	public LinkedList<String> getGainS() {
		return gainS;
	}

	public void setGainS(LinkedList<String> gainS) {
		this.gainS = gainS;
	}

	public LinkedList<Integer> getGainV() {
		return gainV;
	}

	public void setGainV(LinkedList<Integer> gainV) {
		this.gainV = gainV;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
