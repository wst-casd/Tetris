
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent ;
import java.awt.event.KeyListener ;

public class Events implements KeyListener,ActionListener,ItemListener{

	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand().equals("¿ªÊ¼ÓÎÏ·") )
		{
			MainControl.stateFlag = 1 ;
		}
		else {
			MainControl.stateFlag = 0 ;
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyType = e.getKeyCode() ;
		
		switch(keyType){
			case KeyEvent.VK_UP :
				MainControl.pressUpKey( );
				break ;
			
			case KeyEvent.VK_DOWN :
				MainControl.downMove( );
				break ;
			
			case KeyEvent.VK_LEFT :
				MainControl.pressLeftKey() ;
				break ;
			
			case KeyEvent.VK_RIGHT  :
				MainControl.pressRightkey( ) ;
				break ;
		}		
	}
	
	public void itemStateChanged(ItemEvent e) {
		switch ( DrawInterface.difficultyChoice.getSelectedIndex() ) {
		case 0 :
			MainControl.difficultyLevel = 400 ;
			break;
			
		case 1 :
			MainControl.difficultyLevel = 250 ;
			break;
			
		case 2 :
			MainControl.difficultyLevel = 150 ;
			break;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
	
	}

}
