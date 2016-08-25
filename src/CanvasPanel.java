import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class CanvasPanel extends Canvas{
	/**********成员变量**********/
	final static int num = 7 ;
	private  String[] filename = new String[num] ;
	private  Image[] imgs =new Image[num] ;
	private Image bufferImage ;
	private Graphics bufferGraphics ;
	/**********构造方法*******/
	public CanvasPanel(){
		for(int counter=0 ; counter < num ; counter++){
			filename[counter] = new String("BlockImage//BlockImage"+counter+".jpg") ;
			imgs[counter] = Toolkit.getDefaultToolkit().getImage(filename[counter]) ;
		}
	} 
	
	public void paint(Graphics g){
		super.paint(g) ;
		bufferImage = createImage(getSize().width,getSize().height);
		bufferGraphics = bufferImage.getGraphics();
		bufferGraphics.fillRect(0, 0, getSize().width, getSize().height);
		for(int counter1 = 4 ; counter1< 26 ; counter1++)
			for(int counter2 = 0 ; counter2 < 12 ; counter2 ++)
			{
				if( MainControl.boxArray[counter1][counter2].existBlock == 1 )
					bufferGraphics.drawImage(imgs[MainControl.boxArray[counter1][counter2].blockType], 15*counter2 , 15*(counter1-4) , this);
			}
		g.drawImage(bufferImage,0,0,this) ;
		}
	}