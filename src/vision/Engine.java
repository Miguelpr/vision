package vision;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Engine {
	
	static int red=16711680;
	public static BufferedImage load(String source){
		
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	
	public static void save(BufferedImage img, String name){
		
		try {
		    // retrieve image

		    File outputfile = new File(name);
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public BufferedImage mixImages(BufferedImage a, BufferedImage b){
		
		boolean par=false;
		int color=0;
		for (int x=0;x<a.getWidth();x++){
			
			for (int y=0;y<a.getHeight();y++){
				
				
				if (par){
					
					color=b.getRGB(x, y);
					a.setRGB(x, y, color);
					par=false;
				}else{
					
					par=true;
				}
			}
		}
		return a;
	}
	
	
	public void tranform(){
		BufferedImage img= load("1.jpg");
		BufferedImage img2= load("2.jpg");
		//img=mixImages(img,img2);

		//Graphics g = img.getGraphics();
		//g.drawLine(0, 0, img.getWidth(), img.getHeight());
	
		int sidePixels=64;
		int halfSidePixels=(sidePixels/4)-1;
		
		BufferedImage imgResultSmall1= new BufferedImage(sidePixels,sidePixels, BufferedImage.TYPE_INT_RGB);
		BufferedImage imgResultSmall2= new BufferedImage(sidePixels,sidePixels, BufferedImage.TYPE_INT_RGB);
		
		int incPoints=sidePixels*2;

		
		float widthIncrement=(float)img.getWidth()/(float)incPoints;
		float doubleWidthIncrement=widthIncrement*2;
		float heightIncrement=(float)img.getHeight()/(float)incPoints;
		float doubleHeightIncrement=heightIncrement*2;
		
		
		System.out.println("TW:"+img.getWidth()+" TH:"+img.getHeight());

		System.out.println("widthIncrement:"+widthIncrement+" heightIncrement:"+heightIncrement);
		
		System.out.println("doubleWidthIncrement:"+doubleWidthIncrement+" doubleHeightIncrement:"+doubleHeightIncrement);

		int color=0;
		int color2=0;
		boolean found=false;
		for (int x=0;x<sidePixels; x++){
			for (int y=0;y<sidePixels; y++){
				
				int cuadX=(int) (doubleWidthIncrement*x);
				int cuadY=(int) (doubleHeightIncrement*y);
				
				int w=(int) (widthIncrement+cuadX);
				int h=(int) (heightIncrement+cuadY);
				//System.out.println("W:"+w+" H:"+h);
				
				color=img.getRGB(w, h);
				color2=img2.getRGB(w, h);

				imgResultSmall1.setRGB(x, y, color);
				imgResultSmall2.setRGB(x, y, color2);
				
				for (int xS=0;xS<doubleWidthIncrement; xS++){
					for (int yS=0;yS<doubleHeightIncrement; yS++){
						
						//img.setRGB(xS+cuadX, yS+cuadY, color);

						//img2.setRGB(xS+cuadX, yS+cuadY, color2);
					}
				}
				found=false;
				
				//System.out.println("X:"+x+" Y:"+y+"W:"+w+" H:"+h);
				for (int ringIndex=0;ringIndex<halfSidePixels;ringIndex++){
					//System.out.println(">"+ringIndex);
					for (int xRingIndex=-ringIndex; xRingIndex<=ringIndex;xRingIndex++){
						for (int yRingIndex=-ringIndex; yRingIndex<=ringIndex;yRingIndex++){
							
							color2=img2.getRGB(w+xRingIndex, h+yRingIndex);

							if (Math.abs(color-color2)<100){
								img2.setRGB(w+xRingIndex, h+yRingIndex, red);
								System.out.println("Found");
								found=true;
								break;
							}
						}
						if (found){
							break;
						}
					}
					if (found){
						break;
					}
				}
				

	
				
	
				
				
				//img.setRGB(w, h, red);
				img2.setRGB(w, h, red);
				
				
			}
			
		}

		
		
		

		save(img,"result1.png");
		save(img2,"result2.png");
		
		save(imgResultSmall1,"r1.png");
		save(imgResultSmall2,"r2.png");
		
	}

	public static void spiral(int X, int Y) {
	    int x1=0, y1=0, dx = 0, dy = -1;
	    int t = Math.max(X,Y);
	    int maxI = t*t;

	    for (int i=0; i < maxI; i++){
	        if ((-X/2 <= x1) && (x1 <= X/2) && (-Y/2 <= y1) && (y1 <= Y/2)) {
	            System.out.println(x1+","+y1);
	            //DO STUFF
	        }

	        if( (x1 == y1) || ((x1 < 0) && (x1 == -y1)) || ((x1 > 0) && (x1 == 1-y1))) {
	            t=dx; dx=-dy; dy=t;
	        }   
	        x1+=dx; y1+=dy;
	    }
	}
	
	
	
	
	
	public static void main(String[] args) {

		Engine myEngine = new Engine();
		
		myEngine.tranform();
		
		
	}

}
