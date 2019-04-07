package package1;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GraphGui extends Application {

	private Graph g = Graph.getInstance();
	private int[][] graph = g.getGraph();
	private String[][] gain = g.getGain();
	private final int  startX = 100;
	private final int startY = 350;
	private final int space = 200;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
		Scene scene = new Scene(root,400,500);
		primaryStage.setScene(scene);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
		
        Group g = new Group();
        
        for (int i = 0; i < graph.length; i++) {
        	Circle c = new Circle();
        	c.setCenterX(startX + i*space);
        	c.setCenterY(startY);
        	c.setRadius(15);
        	g.getChildren().add(c);
        }
        for (int i = 0; i < graph.length; i++) {
        	for (int j = 0; j < graph.length; j++) {
        		if (graph[i][j] == 1 && (j - i)==1) {
        			Line l = new Line();
        			l.setStartX(startX+i*space);
        			l.setStartY(startY);
        			l.setEndY(startY);
        			l.setEndX(startX+(i+1)*space);
        			g.getChildren().add(l);
        			double[] doubleArray = {
        				startX+i*space+space/2,startY-5,
        				startX+i*space+space/2,startY+5,
        				startX+i*space+space/2+5,startY
        			};
        			Polygon t = new Polygon(doubleArray);
        			g.getChildren().add(t);
        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+i*space+space/2);
        			text.setY(startY - 20);
        			g.getChildren().add(text);
        		} else if (graph[i][j] == 1 && (j - i) > 1) {
        			QuadCurve q = new QuadCurve();
        			q.setStartX(startX+i*space);
        			q.setEndX(startX+j*space);
        			q.setStartY(startY);
        			q.setEndY(startY);
        			q.setControlX(startX+((j+i)/2)*space);
        			q.setControlY(startY-space-j*20);
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			g.getChildren().add(q);
        			//direction
        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+((i+j+1)/2)*space+space/2);
        			text.setY(startY -space/2);
        			g.getChildren().add(text);
        		} else if (graph[i][j] == 1 && (j - i) < 0) {
        			QuadCurve q = new QuadCurve();
        			q.setStartX(startX+i*space);
        			q.setEndX(startX+j*space);
        			q.setStartY(startY);
        			q.setEndY(startY);
        			q.setControlX(startX+((j+i)/2)*space+space/2);
        			q.setControlY(startY+space+j*20);
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			g.getChildren().add(q);
        			//direction
        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+((i+j)/2)*space+space/2);
        			text.setY(startY + space/2);
        			g.getChildren().add(text);
        		} else if (graph[i][j] == 1 && (j - i) == 0) {
        			QuadCurve q = new QuadCurve();
        			q.setStartX(startX+i*space + 9);
        			q.setEndX(startX+j*space - 9);
        			q.setStartY(startY);
        			q.setEndY(startY);
        			q.setControlX(startX+i*space);
        			q.setControlY(startY+space/2);
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			g.getChildren().add(q);
        			//direction
        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+i*space);
        			text.setY(startY +70);
        			g.getChildren().add(text);
        		}
        	}
        }
        root.getChildren().add(g);
		primaryStage.show();
	}

}
