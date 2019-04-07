package package1;

import java.util.ArrayList;
import java.util.LinkedList;

public class Delta {

	private LinkedList<Loop> loops;
	private LinkedList<Loop> nonTouching;
	private ArrayList<LinkedList<Loop>> combination = new ArrayList<LinkedList<Loop>>();
	private boolean flag = false;
	private String symbol = "";
	private float value = 1;

	public Delta(LinkedList<Loop> loops) {
		this.loops = loops;
	}

	public ArrayList<LinkedList<Loop>> getCombination() {
		return combination;
	}

	public void setCombination(ArrayList<LinkedList<Loop>> combination) {
		this.combination = combination;
	}

	public float getValue() {
		return value;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void getNonTouchingLoops(LinkedList<Loop> x) {
		nonTouching = new LinkedList<Loop>();
		if (!flag) {
			for (int i = 0; i < loops.size(); i++) {
				Loop first = loops.get(i);
				for (int j = i+1; j < x.size(); j++) {
					Loop second = x.get(j);
					boolean touching = false;
					Loop nonTouchingLoop = new Loop();
					for (int k = 0; k < first.getNodes().size(); k++) {
						for (int l = 0; l < second.getNodes().size(); l++) {
							if (first.getNodes().get(k).equals(second.getNodes().get(l))) {
								touching = true;
								break;
							} else {
								nonTouchingLoop.getNodes().add(first.getNodes().get(k));
								nonTouchingLoop.getNodes().add(second.getNodes().get(l));
							}
						}
						if (touching) {
							break;
						}
					}
					if (!touching) {
						nonTouchingLoop.setGain(second.getGain() + first.getGain());
						nonTouchingLoop.setGainVal(second.getGainVal() * first.getGainVal());
						nonTouchingLoop.getNonTouching().add(i);
						nonTouchingLoop.getNonTouching().add(j);
						nonTouching.add(nonTouchingLoop);
					}
				}
			}
			return;
		}
		for (int i = 0; i < loops.size(); i++) {
			Loop first = loops.get(i);
			for (int j = 0; j < x.size(); j++) {
				Loop second = x.get(j);
				boolean touching = false;
				Loop nonTouchingLoop = new Loop();
				for (int k = 0; k < first.getNodes().size(); k++) {
					for (int l = 0; l < second.getNodes().size(); l++) {
						if (first.getNodes().get(k).equals(second.getNodes().get(l))) {
							touching = true;
							break;
						} else {
							nonTouchingLoop.getNodes().add(first.getNodes().get(k));
							nonTouchingLoop.getNodes().add(second.getNodes().get(l));
						}
					}
					if (touching) {
						break;
					}
				}
				if (!touching) {
					nonTouchingLoop.setGain(second.getGain() + first.getGain());
					nonTouchingLoop.setGainVal(second.getGainVal() * first.getGainVal());
					nonTouchingLoop.getNonTouching().add(i);
					for (int z = 0; z < second.getNonTouching().size(); z++) {
						nonTouchingLoop.getNonTouching().add(second.getNonTouching().get(z));
					}
					nonTouching.add(nonTouchingLoop);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void Value() {
		boolean sign = true;
		for (int i = 0; i < loops.size(); i++) {
			Loop x = loops.get(i);
			if (x.getGain().length() == 0) {
				value -= x.getGainVal();
			} else {
				if (x.getGainVal() ==1) {
					symbol += "-" + x.getGain();
				} else if (x.getGainVal() == -1) {
					symbol += "+" + x.getGain();
				} else if (x.getGainVal() < 0){
					symbol += "+" + String.valueOf(x.getGainVal()) + x.getGain();
				} else {
					symbol += "-" + String.valueOf(x.getGainVal()) + x.getGain();
				}
			}
		}
		getNonTouchingLoops(loops);
		flag = true;
		while (!nonTouching.isEmpty()) {
			combination.add((LinkedList<Loop>) nonTouching.clone());
			for (int i = 0; i < nonTouching.size(); i++) {
				Loop x = nonTouching.get(i);
				if (x.getGain().length() == 0) {
					if (sign) {
						value += x.getGainVal();
					} else {
						value -= x.getGainVal();
					}
				} else {
					if (sign) {
						if (x.getGainVal() ==1) {
							symbol += "+" + x.getGain();
						} else if (x.getGainVal() == -1) {
							symbol += "-" + x.getGain();
						} else if (x.getGainVal() < 0){
							symbol += "-" + String.valueOf(x.getGainVal()) + x.getGain();
						} else {
							symbol += "+" + String.valueOf(x.getGainVal()) + x.getGain();
						}
					} else {
						if (x.getGainVal() ==1) {
							symbol += "-" + x.getGain();
						} else if (x.getGainVal() == -1) {
							symbol += "+" + x.getGain();
						} else if (x.getGainVal() < 0){
							symbol += "+" + String.valueOf(x.getGainVal()) + x.getGain();
						} else {
							symbol += "-" + String.valueOf(x.getGainVal()) + x.getGain();
						}
					}
				}
			}
			if (sign) {
				sign = false;
			} else {
				sign = true;
			}
			getNonTouchingLoops(nonTouching);
		}
	}
}
