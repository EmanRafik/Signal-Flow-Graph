package package1;

import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GraphGui extends Application {

	private Graph gr = Graph.getInstance();
	private final int  startX = 100;
	private final int startY = 350;
	private final int space = 200;
	private final int radius = 15;
	private int forwardConst = -60;
	private int backwardConst = -60;
	private final double strokeWidth = 2;
	
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ScrollPane sp = new ScrollPane();
		Group root = new Group();
		Group r = new Group();
		Scene scene = new Scene(root,400,500);
		primaryStage.setScene(scene);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
		
        Group g = new Group();
        int[][] graph = gr.getGraph();
    	String[][] gain = gr.getGain();
        
        for (int i = 0; i < graph.length; i++) {
        	Circle c = new Circle();
        	c.setCenterX(startX + i*space);
        	c.setCenterY(startY);
        	c.setRadius(radius);
        	c.setFill(Color.TRANSPARENT);
        	c.setStrokeWidth(strokeWidth);
        	c.setStroke(Color.BLACK);
        	g.getChildren().add(c);
        	
        	Text t = new Text();
        	t.setText(Integer.toString(i));
        	t.setX(startX + i*space);
        	t.setY(startY);
        	g.getChildren().add(t);
        }
        for (int i = 0; i < graph.length; i++) {
        	for (int j = 0; j < graph.length; j++) {
        		if (graph[i][j] == 1 && (j - i)==1) {
        			Line l = new Line();
        			l.setStartX(startX+i*space+radius);
        			l.setStartY(startY);
        			l.setEndY(startY);
        			l.setEndX(startX+(i+1)*space-radius);
        			l.setStrokeWidth(strokeWidth);
        			g.getChildren().add(l);
        			double[] doubleArray = {
        				startX+j*space-radius-10,startY-10,
        				startX+j*space-radius-10,startY+10,
        				startX+j*space-radius,startY
        			};
        			Polygon t = new Polygon(doubleArray);
        			t.setStroke(Color.BLACK);
        			t.setFill(Color.TRANSPARENT);
        			t.setStrokeWidth(strokeWidth);
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
        			q.setStartY(startY-radius);
        			q.setEndY(startY-radius);
        			q.setControlX(startX+((j+i)/2)*space);
        			q.setControlY(startY-space-j*20-forwardConst);
        			q.setStrokeWidth(strokeWidth);
        			forwardConst+=20;
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			g.getChildren().add(q);

        			double[] doubleArray = {
            			startX + j*space,startY -radius,
            			startX + j*space-20, startY -radius-20,
            			startX + j*space-20,startY -radius
            		};
            		Polygon t = new Polygon(doubleArray);
            		t.setStroke(Color.BLACK);
            		t.setFill(Color.TRANSPARENT);
            		t.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            		t.setStrokeWidth(strokeWidth);
            		g.getChildren().add(t);
            			
        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+((i+j+1)/2)*space+space/2);
        			text.setY(startY -space/2);
        			g.getChildren().add(text);
        		} else if (graph[i][j] == 1 && (j - i) < 0) {
        			QuadCurve q = new QuadCurve();
        			q.setStartX(startX+i*space);
        			q.setEndX(startX+j*space);
        			q.setStartY(startY+radius);
        			q.setEndY(startY+radius);
        			q.setControlX(startX+((j+i)/2)*space+space/2);
        			q.setControlY(startY+space+j*20+backwardConst);
        			backwardConst+=20;
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			q.setStrokeWidth(strokeWidth);
        			g.getChildren().add(q);
        			
        			double[] doubleArray = {
                			startX + j*space,startY +radius,
                			startX + j*space+20, startY +radius+20,
                			startX + j*space+20,startY +radius
                		};
                		Polygon t = new Polygon(doubleArray);
                		t.setStroke(Color.BLACK);
                		t.setFill(Color.TRANSPARENT);
                		t.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                		t.setStrokeWidth(strokeWidth);
                		g.getChildren().add(t);

        			Text text = new Text();
        			text.setText(gain[i][j]);
        			text.setX(startX+((i+j)/2)*space+space/2);
        			text.setY(startY + space/2);
        			g.getChildren().add(text);
        		} else if (graph[i][j] == 1 && (j - i) == 0) {
        			QuadCurve q = new QuadCurve();
        			q.setStartX(startX+i*space + 9);
        			q.setEndX(startX+j*space - 9);
        			q.setStartY(startY+radius);
        			q.setEndY(startY+radius);
        			q.setControlX(startX+i*space);
        			q.setControlY(startY+space/2);
        			q.setStroke(Color.BLACK);
        			q.setFill(Color.TRANSPARENT);
        			q.setStrokeWidth(strokeWidth);
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
        r.getChildren().add(g);
        //root.getChildren().add(g);
        sp.setPannable(true);
        sp.setPrefSize(primaryScreenBounds.getWidth()-50, primaryScreenBounds.getHeight()-50);
        r.setLayoutX(startX);
        r.setLayoutY(startY);
        sp.setContent(r);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        root.getChildren().add(sp);
		primaryStage.show();
	}

}
