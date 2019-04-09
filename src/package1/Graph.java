package package1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Pair;

public class Graph {

	private static Graph instance = null;
	private LinkedList<Path> paths = new LinkedList<Path>();
	private LinkedList<Path> temp = new LinkedList<Path>();
	private LinkedList<Loop> loops = new LinkedList<Loop>();
	private int n = 4;
	// private int[][] graph;
	// private String[][] gain;
	///
	private int[][] graph = { { 0, 1, 0, 0 }, { 0, 0, 1, 1 }, { 0, 1, 0, 1 }, { 0, 1, 1, 0 } };
	private String[][] gain = { { "0", "1", "0", "0" }, { "0", "0", "1", "1" }, { "0", "-1", "0", "1" },
			{ "0", "-1", "-1", "0" } };
	Path pathmain = new Path();
	LinkedList<Integer> nodes = new LinkedList<Integer>();
	///
	private LinkedList<Integer> path = new LinkedList<Integer>();
	private LinkedList<String> gainSympol = new LinkedList<String>();
	private LinkedList<Float> gainValue = new LinkedList<Float>();
	private LinkedList<Integer> pathb = new LinkedList<Integer>();
	private LinkedList<String> gainSympolb = new LinkedList<String>();
	private LinkedList<Float> gainValueb = new LinkedList<Float>();
	private LinkedList<Path> pathsb = new LinkedList<Path>();
	@SuppressWarnings("rawtypes")
	private LinkedList<Pair> reqP = new LinkedList<Pair>();

	@SuppressWarnings("rawtypes")
	public LinkedList<Pair> getReqP() {
		return reqP;
	}

	public void setReqP(@SuppressWarnings("rawtypes") LinkedList<Pair> reqP) {
		this.reqP = reqP;
	}

	private ArrayList<LinkedList<Loop>> nonTouchingLoops;
	private Delta delta;

	private Graph() {
	}

	public static synchronized Graph getInstance() {
		if (instance == null) {
			instance = new Graph();
		}
		return instance;
	}

	public ArrayList<LinkedList<Loop>> getNonTouchingLoops() {
		return nonTouchingLoops;
	}

	public void setNonTouchingLoops(ArrayList<LinkedList<Loop>> nonTouchingLoops) {
		this.nonTouchingLoops = nonTouchingLoops;
	}

	public LinkedList<Path> getPaths() {
		return paths;
	}

	public LinkedList<Loop> getLoops() {
		return loops;
	}

	public void setLoops(LinkedList<Loop> loops) {
		this.loops = loops;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int[][] graph) {
		this.graph = graph;
	}

	public String[][] getGain() {
		return gain;
	}

	public void setGain(String[][] gain) {
		this.gain = gain;
	}

	public Delta getDelta() {
		return delta;
	}

	public void setDelta() {
		delta = new Delta(loops);
		delta.Value();
		for (int i = 0; i < paths.size(); i++) {
			paths.get(i).setDelta();
		}
		nonTouchingLoops = delta.getCombination();
	}

	public LinkedList<Path> getPathsb() {
		return pathsb;
	}

	public void setPathsb(LinkedList<Path> pathsb) {
		this.pathsb = pathsb;
	}

	public String transferFunction() {
		String s = String.valueOf(delta.getValue()) + delta.getSymbol();
		String tf = "";
		String temp = "";
		float value = 0;
		for (int i = 0; i < paths.size(); i++) {
			Path p = paths.get(i);
			if (p.getGain().length() == 0 && p.getDelta().getSymbol().length() == 0) {
				value += p.getGainVal()*p.getDelta().getValue();
			} else if (p.getGain().length() == 0) {
				String d = String.valueOf(p.getDelta().getValue()) + p.getDelta().getSymbol();
				if (p.getGainVal() == 1) {
					temp += "("+d+")";
				} else if (p.getGainVal() == -1) {
					temp+="((-)"+"("+d+"))";
				} else {
					temp+="("+String.valueOf(p.getGainVal())+"("+d+"))";
				}
			} else if (p.getDelta().getSymbol().length() == 0) {
				temp += String.valueOf(p.getGainVal()*p.getDelta().getValue()) + p.getGain();
			} else {
				String d = String.valueOf(p.getDelta().getValue()) + p.getDelta().getSymbol();
				String pGain;
				if (p.getGainVal() == 1) {
					pGain = p.getGain();
				} else if (p.getGainVal() == -1) {
					pGain = "-" + p.getGain();
				} else {
					pGain = String.valueOf(p.getGainVal()) + p.getGain();
				}
				temp += "(" + pGain + ")(" + d + ")";
			}
		}
		if (temp.length() == 0 && delta.getSymbol().length() == 0) {
			tf = String.valueOf(value/delta.getValue());
		} else if (temp.length() == 0) {
			tf = String.valueOf(value) + "/" + s;
		} else {
			if (value != 0) {
				tf = "(" + String.valueOf(value) + temp + ")/(" + s + ")";
			} else {
				tf = "(" + temp + ")/(" + s + ")";
			}
		}
		return tf;
	}

	@SuppressWarnings("unchecked")
	public void setPaths() {
		paths = (LinkedList<Path>) path(0, n - 1).clone();
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Path> path(int a, int b) {
		path.add(a);
		if (a == b) {
			Path p = new Path();
			p.setNodes((LinkedList<Integer>) path.clone());
			float val = 1;
			String symbol = "";
			for (int i = 0; i < gainValue.size(); i++) {
				val *= gainValue.get(i);
			}
			for (int i = 0; i < gainSympol.size(); i++) {
				String x = gainSympol.get(i);
				if (x.length() > 0) {
					if (x.charAt(0) == '-') {
						val *= -1;
						x = x.substring(1);
					}
				}
				symbol += x;
			}
			p.setGain(symbol);
			p.setGainVal(val);
			temp.add(p);
			path.removeLast();
			gainValue.removeLast();
			gainSympol.removeLast();
		} else if (a > b) {
			path.removeLast();
			gainValue.removeLast();
			gainSympol.removeLast();
		} else {
			for (int i = a + 1; i < n; i++) {
				if (graph[a][i] != 0) {

					String c = gain[a][i];
					if (isNumeric(c)) {
						gainValue.add(Float.parseFloat(c));
						gainSympol.add("");
					} else {
						gainSympol.add(c);
						gainValue.add((float) 1);
					}
					path(i, b);
				}
			}
			if (gainValue.size() != 0) {
				path.removeLast();
				gainValue.removeLast();
				gainSympol.removeLast();
			}
		}
		// paths = (LinkedList<Path>) temp.clone();
		return temp;
	}

	public LinkedList<Path> getTemp() {
		return temp;
	}

	public void setTemp(LinkedList<Path> temp) {
		this.temp = temp;
	}

	public LinkedList<Integer> getPath() {
		return path;
	}

	public void setPath(LinkedList<Integer> path) {
		this.path = path;
	}

	public LinkedList<String> getGainSympol() {
		return gainSympol;
	}

	public void setGainSympol(LinkedList<String> gainSympol) {
		this.gainSympol = gainSympol;
	}

	public LinkedList<Float> getGainValue() {
		return gainValue;
	}

	public void setGainValue(LinkedList<Float> gainValue) {
		this.gainValue = gainValue;
	}

	public boolean isNumeric(String strNum) {
		String REGEX = "(-)?\\d+(\\.\\d+)?";
		Pattern pattern = Pattern.compile(REGEX);
		Matcher m = pattern.matcher(strNum);
		if (m.find() && m.start() == 0 && m.end() == strNum.length()) {
			return true;
		}
		return false;
	}

	public void loops() {
		temp = new LinkedList<Path>();
		for (int i = 0; i < n; i++) { // i is the node i want a cycle start and end at.
			for (int k = i + 1; k < n; k++) { // k is the nodes after node i.
				pathsb = new LinkedList<Path>();
				getPathb().clear();
				getGainValueb().clear();
				getGainSympolb().clear();
				backwardPaths(i, k);
				temp = new LinkedList<Path>();
				path.clear();
				gainValue.clear();
				gainSympol.clear();
				temp = path(i, k);
				for (int q = 0; q < temp.size(); q++) {
					for (int j = 0; j < pathsb.size(); j++) {
						boolean flag = true;
						Path backPath = pathsb.get(j);
						Path forwardPath = temp.get(q);
						LinkedList<Integer> back = backPath.getNodes();
						LinkedList<Integer> forward = forwardPath.getNodes();
						for (int x = 1; x < back.size() - 1; x++) {
							for (int y = 1; y < forward.size() - 1; y++) {
								if (back.get(x).equals(forward.get(y))) {
									flag = false;
									break;
								}
							}
							if (!flag) {
								break;
							}
						}
						if (flag) {
							Loop loop = new Loop();// loop on all paths and add integer i "new node" to.
							loop.setNodes(forwardPath.getNodes());
							for (int z = 1; z < back.size(); z++) {
								loop.getNodes().add(back.get(z));
							}
							loop.setGainVal(backPath.getGainVal() * forwardPath.getGainVal());
							loop.setGain(backPath.getGain() + forwardPath.getGain());
							loops.add(loop);
						}
					}
				}
			}
			if (graph[i][i] != 0) { // self loop
				Loop loop = new Loop();
				loop.getNodes().add(i);
				String c = gain[i][i];
				if (isNumeric(c)) {
					loop.setGainVal(loop.getGainVal() * Float.parseFloat(c));
				} else {
					loop.setGain(loop.getGain() + c);
				}
				loops.add(loop);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void backwardPaths(int a, int b) { // a is the node i want to go to
		getPathb().add(b);
		// k is the node i try to find path from
		if (a == b) {
			Path p = new Path();
			p.setNodes((LinkedList<Integer>) getPathb().clone());
			float val = 1;
			String symbol = "";
			for (int i = 0; i < getGainValueb().size(); i++) {
				val *= getGainValueb().get(i);
			}
			for (int i = 0; i < getGainSympolb().size(); i++) {
				String x = getGainSympolb().get(i);
				if (x.length() > 0) {
					if (x.charAt(0) == '-') {
						val *= -1;
						x = x.substring(1);
					}
				}
				symbol += x;
			}
			p.setGain(symbol);
			p.setGainVal(val);
			pathsb.add(p);
			getPathb().removeLast();
			getGainValueb().removeLast();
			getGainSympolb().removeLast();
		} else if (b < a) {
			getPathb().removeLast();
			getGainValueb().removeLast();
			getGainSympolb().removeLast();
		} else {
			for (int i = b - 1; i >= a; i--) {
				if (graph[b][i] != 0) {

					String c = gain[b][i];
					if (isNumeric(c)) {
						getGainValueb().add(Float.parseFloat(c));
						getGainSympolb().add("");
					} else {
						getGainSympolb().add(c);
						getGainValueb().add((float) 1);
					}
					backwardPaths(a, i);
				}
			}
			if (getGainValueb().size() != 0) {
				getPathb().removeLast();
				getGainValueb().removeLast();
				getGainSympolb().removeLast();
			}
		}
	}

	public LinkedList<Integer> getPathb() {
		return pathb;
	}

	public void setPathb(LinkedList<Integer> pathb) {
		this.pathb = pathb;
	}

	public LinkedList<Float> getGainValueb() {
		return gainValueb;
	}

	public void setGainValueb(LinkedList<Float> gainValueb) {
		this.gainValueb = gainValueb;
	}

	public LinkedList<String> getGainSympolb() {
		return gainSympolb;
	}

	public void setGainSympolb(LinkedList<String> gainSympolb) {
		this.gainSympolb = gainSympolb;
	}
}
