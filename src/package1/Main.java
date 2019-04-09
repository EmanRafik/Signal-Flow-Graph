package package1;

import java.util.LinkedList;

public class Main {

	static Graph g = Graph.getInstance();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Path> ways = new LinkedList<Path>();
		LinkedList<Loop> loops = new LinkedList<Loop>();
		Path path = new Path();
		LinkedList <Integer> nodes = new LinkedList <Integer>();
		int[][] array1= {{0,1,0,0,0,0},{0,0,1,1,1,0},{0,1,0,1,0,0},{0,0,1,1,1,0},{0,0,0,0,0,1},{0,0,0,0,0,0}};
		int[][] arrayG1= {{0,1,0,0,0,0},{0,0,1,1,1,0},{0,-1,0,1,0,0},{0,0,-1,-1,1,0},{0,0,0,0,0,1},{0,0,0,0,0,0}};
		//ways = g.path(0,4);
		g.setPaths();

		g.loops();
		g.setDelta();
		/*for(int i = 0;i < ways.size();i++) {
			path = ways.get(i);
			nodes = path.getNodes();
			for (int j =0;j< nodes.size();j++) {
				System.out.print(nodes.get(j)+",");
			}
			System.out.println("\n");
			System.out.println("Gain = "+path.getGainVal());
			//path.setDelta();
		}*/
		
		/*loops = g.getLoops();
		for(int i = 0;i < loops.size();i++) {
			Loop loop = loops.get(i);
			nodes = loop.getNodes();
			for (int j =0;j< nodes.size();j++) {
				System.out.print(nodes.get(j)+",");
			}
			System.out.println("\n");
			System.out.println("Gain = "+loop.getGainVal()); 
		}*/
		
		
		
		/*g.backwardPaths(1,3);
		ways = g.getPathsb();
		for(int i = 0;i < g.getPathsb().size();i++) {
			path = ways.get(i);
			nodes = path.getNodes();
			for (int j =0;j< nodes.size();j++) {
				System.out.print(nodes.get(j)+",");
			}
			System.out.println("\n");
			System.out.println("Gain = "+path.getGainVal());
		}*/
		System.out.println(g.getDelta().getValue());
		System.out.println(g.transferFunction());
	}

}
