
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class DrawInterface extends JFrame{
	/********��Ա����************/
	ImageIcon 	bgImage;                             
	JLabel 			bgLabel;
	JLabel 			previewLabel;
	JLabel 			scoreLabel;
	JLabel 			difffucultyJLabel ;
	
	static Choice difficultyChoice ;

	/*Canvas previewCanvas = new Canvas( );*/
	
	Button 				stateButton ;
	JTextField 			scoreJTextField ;
	CanvasPanel 	gameCanvas ;
	Container 		container ;
	
	/*****���췽��******/
	public DrawInterface(){
		bgImage 				= 		new ImageIcon("background.jpg");
		bgLabel  				= 		new JLabel(bgImage);
		previewLabel 		= 		new JLabel("����Ԥ��",JLabel.CENTER) ;
		scoreLabel 			= 		new JLabel("�÷�" , JLabel.CENTER);
		difffucultyJLabel 	= 		new JLabel( "�Ѷȵȼ�" , JLabel.CENTER ) ;
		
		difficultyChoice   	=   	new Choice( ) ; 
		stateButton         	=   	new Button("��ʼ��Ϸ") ;
		scoreJTextField    	=   	new JTextField( ) ;
		gameCanvas       	= 		new CanvasPanel() ;
		container 				= 		getContentPane( ) ;
	}
	
	/******���������********/
	public void drawInterface(){
		
		setLayout(null);                //���ý��沼��Ϊ��
		setSize(500,450);               //����������Ĵ�С
		
		previewLabel.setBounds(330 , 50 , 60 , 15);
		scoreLabel.setBounds(270, 205, 30, 15);
		difffucultyJLabel.setBounds(255 , 255 , 60 , 15 ) ;
		
		scoreJTextField.setHorizontalAlignment(JTextField.CENTER);
		scoreJTextField.setBounds(310 ,  200 , 100 , 25 );
		stateButton.setBounds(270, 310, 60, 40);
		gameCanvas.setBackground(new Color( 150 , 220 , 180 ));
		gameCanvas.setBounds(40, 40, 180, 330) ;
		difficultyChoice.setBounds( 325 , 250 , 50 , 20 ) ;
		
		container.add(previewLabel);
		container.add(scoreLabel);
		container.add(difffucultyJLabel) ;
		container.add(scoreJTextField) ;
		container.add(stateButton) ;
		container.add(gameCanvas) ;
		container.add(difficultyChoice) ;
		
		difficultyChoice.addItem( "һ��" ) ;
		difficultyChoice.addItem( "�е�" ) ;
		difficultyChoice.addItem( "����" ) ;
		
		gameCanvas.addKeyListener( new Events() ) ;
		stateButton.addActionListener( new Events() ) ;
		difficultyChoice.addItemListener( new Events( )) ;
		
		getLayeredPane().add(bgLabel,new Integer(Integer.MIN_VALUE)) ;
		bgLabel.setBounds(0,0,bgImage.getIconWidth(),bgImage.getIconHeight( )) ;
		((JPanel)container).setOpaque(false) ;            //����contentPane��͸��
		
		setTitle("����˹����") ;
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE) ;
		setVisible(true) ;
		
		displayScore( 0 ) ;
		getFocus( ) ;
	}
	
	public void drawGameState( ){
		gameCanvas.paint(gameCanvas.getGraphics( )) ;
	}
	
	protected void setButtonTitle( int flag ){
		if( flag == 1 )
		{
			stateButton.setLabel( "��ͣ" ) ;
			getFocus() ;
		}
		else {
			stateButton.setLabel( "��ʼ��Ϸ" ) ;
			getFocus() ;
		}
	}
	
	protected void getFocus(){
		gameCanvas.requestFocus( ) ;
	}
	
	protected void displayScore( int currentScore ){
		scoreJTextField.setText( "" + currentScore ) ;
	}
	
}
