package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Main extends Application {
	int height = 1300;
	int width = 1300;
	Random generator = new Random();
	int max = 2550;

	public  void saveToFile(Image image) {
		File file = new File(this.getParameters().getRaw().get(0)+System.currentTimeMillis()+".png");
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
			ImageIO.write(
			        renderedImage, 
			        "png",
			        file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	
	
	public void print(PixelWriter p){
		Color color;
		int factor = 1;
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				double c_re = (col/factor - width / 2.0) * 4.0 / width;
				double c_im = (row/factor - height / 2.0) * 4.0 / width;
				double x = 0, y = 0;
				int iteration = 0;
				double x_new;
				while (x * x + y * y <= 4 && iteration < max) {
					x_new  = x * x - y * y + c_re;
					y = 2 * x * y + c_im;
					x = x_new;
					iteration++;
				}
				if (iteration < max) {
					double g =  100*Math.sin(iteration/10);
					if(g< 0){
						g= 255+g;
					}
					double b =( 100*Math.cos(iteration%10));
					if( b < 0){
						b = 255+b;
					}
					color = Color.rgb(127,(int)g,(int)b);
					p.setColor(col, row, color);
			
					p.setColor(col, row, color);

				} else {
					p.setColor(col, row, Color.BLACK);
				}
			}
		}
	}
	@Override
	public void start(Stage primaryStage) {
		try {			
			WritableImage pw = new WritableImage(height, width);
			ImageView c = new ImageView();
			PixelWriter p = pw.getPixelWriter();
			ScrollPane root = new ScrollPane();
			
			c.setImage(pw);
			root.setContent(c);
			Scene scene = new Scene(root, height, width);
			print(p);
			saveToFile(pw);
			System.out.println("a");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
