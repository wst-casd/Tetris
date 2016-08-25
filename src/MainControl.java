
import java.util.Random;

import javax.swing.JOptionPane;

import org.omg.CORBA.PRIVATE_MEMBER;

public class MainControl{
	/********成员变量*********/
	static Blocks[][] boxArray ;
	static int stateFlag ;                                    //用来标识当前是该正在游戏还是暂停，1表示进行游戏，0表示暂停
	private static DrawInterface mainFrame ;
	private Random randomNum ;
	static int currentPiecesType ;
	static int currentPiecesState ;
	static int currentCoordX ;
	static int currentCoordY ;
	static SquareCoord relativeCoords ;
	static int difficultyLevel ;
	private int score ;
	
	/********构造方法*********/
	public MainControl(){
		boxArray = new Blocks[26][12] ;                                       //
		
		//
		for(int counter1 = 0 ; counter1< 26 ; counter1++)
			for(int counter2 = 0 ; counter2 < 12 ; counter2 ++)
				boxArray[counter1][counter2] = new Blocks( ) ;
		
		relativeCoords = new SquareCoord( ) ;
		
		stateFlag = 0 ;
		mainFrame = new DrawInterface( ) ;
		randomNum = new Random() ;

		difficultyLevel = 500 ;
		score = 0 ;
	}
	
	public static void main(String[] args){
		new MainControl( ).mainControl( ) ;
	}
	
	private void mainControl( ){		
		mainFrame.drawInterface( ) ;	
	
		while(true)
		{	
			if( stateFlag == 1 )
			{
				mainFrame.setButtonTitle( stateFlag ) ;
				createPrimaryBlock( ) ;
				setPrimaryLocation( ) ;
				setBlocks( ) ;	
								
				while(true)
				{	
					if( stateFlag == 0 )
					{
						mainFrame.setButtonTitle( stateFlag ) ;
						pauseDeal() ;
						mainFrame.setButtonTitle( stateFlag ) ;
					}
						
					if( downMove( ) == 1 )
					{
						break ;
					}
					for(int counter1 = 0 ; counter1<10000 ; counter1++ )
						for(int counter2  = 0 ; counter2<100*difficultyLevel ; counter2++ )
							;
				}	
				
				clearFullRows( ) ;
				
				if( endDetection() == 1 )
				{
					System.out.println("OK") ;
					
					if( JOptionPane.showConfirmDialog(mainFrame, "得分 ， 是否继续玩？" , "游戏结束" , JOptionPane.YES_NO_OPTION) == 1 )
						System.exit( 1 ) ;
					else {
						stateFlag = 0 ;
						mainFrame.setButtonTitle( stateFlag ) ;
						clearBlocks( 0 ) ;
						mainFrame.displayScore( 0 ) ;
						mainFrame.drawGameState( ) ;
					}	
				}			
			}			
		}
	}
	
	private void createPrimaryBlock( ){
		int number = 0 ;
		number = randomNum.nextInt( ) ;
		currentPiecesType = Math.abs(number%7) ;	
		currentPiecesState = 0 ;
	}
	
	protected static int crashCheck( ){
		int varX ;
		int varY ;
		int existCrash = 0 ;
		
		for(int counter = 0 ; counter < 4 ; counter ++)
		{
			varX = currentCoordX +relativeCoords.relativeCoordX[currentPiecesType][currentPiecesState][counter] ;
			varY = currentCoordY +relativeCoords.relativeCoordY[currentPiecesType][currentPiecesState][counter] ;
			
			if( MainControl.boxArray[varY][varX].existBlock == 1 )
			{
				existCrash = 1 ;
				break ;
			}
		}
		
		if( existCrash == 1 )
			return 1 ;
		else
			return 0 ;
	}
	
	private int endDetection( ){
		for(int counter = 0 ; counter < 12 ; counter++ )
		{
			if( boxArray[4][counter].existBlock == 1 )
			{
				return 1 ;
			}
		}
		return 0 ;
	}
	
	private void clearFullRows( ){
		int   	lineCounter1 	  	= -1 ;
		int 	lineCounter2     	= -1 ;
		int	isFull                 	=  0 ;
		int 	fullLineCounter 	=  0 ;
		
		for( lineCounter1 = 25 ; lineCounter1 >= 4 ; lineCounter1-- )
		{
			for(int counter1 = 0 ; counter1 < 12 ; counter1++)
				if(boxArray[lineCounter1][counter1].existBlock == 0 )
				{
					isFull = 0 ;
					break ;	
				}
				else
					isFull = 1 ;
			
			if( isFull ==1 )
			{
				fullLineCounter ++ ;
				break ;
			}
		}
			
		if( isFull == 1 )
		{
			lineCounter2 = lineCounter1 ;
			lineCounter1-- ;

			for( ; lineCounter1 >= 4 ; lineCounter1-- )
			{
				for( int counter2 = 0 ; counter2 < 12 ; counter2++ )
					if( boxArray[lineCounter1][counter2].existBlock == 0 )
					{
						isFull = 0 ;
						break ;
					}
					else
					{
						isFull = 1 ;
						if( counter2 == 11 )
							fullLineCounter ++ ;
					}
				
				if( isFull  == 0 )
				{
					for( int counter3 = 0 ; counter3 < 12 ; counter3++)
					{
						boxArray[lineCounter2][counter3].setValue(boxArray[lineCounter1][counter3].existBlock, boxArray[lineCounter1][counter3].blockType) ;
					}
					lineCounter2-- ;
				}
			}	
			
			for(int counter4 = 4 ; counter4 <= lineCounter2 ; counter4++)
				for( int counter5 = 0 ; counter5 < 12 ; counter5++ )
					boxArray[counter4][counter5].setValue( 0 , 0 ) ;
			
			mainFrame.drawGameState( ) ;
			scoring( fullLineCounter ) ;
			fullLineCounter = 0 ;
		}
	}

	static void setBlocks(){	
		int varX ;
		int varY ;
		for( int counter = 0 ; counter < 4 ; counter++ )
		{
			varX = currentCoordX +relativeCoords.relativeCoordX[currentPiecesType][currentPiecesState][counter] ;
			varY = currentCoordY +relativeCoords.relativeCoordY[currentPiecesType][currentPiecesState][counter] ;

			boxArray[varY][varX].setValue(1, currentPiecesType) ;
		}
	}
	
	static void clearBlocks( int clearFlag ){
		if( clearFlag == 1 )
			for( int counter = 0 ; counter < 4 ; counter++ )
			{
				int varX = currentCoordX +relativeCoords.relativeCoordX[currentPiecesType][currentPiecesState][counter] ;
				int varY = currentCoordY +relativeCoords.relativeCoordY[currentPiecesType][currentPiecesState][counter] ;
				MainControl.boxArray[varY][varX].setValue(0, 0) ;
			}
		else
		{
			for( int counter1 = 0 ; counter1 <= 25 ; counter1++ )
				for( int counter2 = 0 ; counter2 < 12 ; counter2++ )
				{
					MainControl.boxArray[counter1][counter2].setValue(0, 0) ;
				}		
		}
	}

	private void setPrimaryLocation( ){
		switch(currentPiecesType){
			case 0 :
				currentCoordX  = 4 ;
				currentCoordY  = 0 ;
				break ;
				
			case 1 :
				currentCoordX  = 4 ;
				currentCoordY  = 1 ;
				break ;
				
			case 2 :
				currentCoordX = 4 ;
				currentCoordY = 1 ;
				break ;
				
			case 3 :
				currentCoordX = 4 ;
				currentCoordY = 2 ;
				break ;
				
			case 4 :
				currentCoordX = 4 ;
				currentCoordY = 1 ;
				break ;
				
			case 5 :
				currentCoordX = 4 ;
				currentCoordY = 1 ;
				break ;
				
			case 6 :
				currentCoordX = 4 ;
				currentCoordY = 1 ;
				break ;
		} 
	}

	private void pauseDeal( ){
		while(true)
		{
			if( stateFlag == 1 )
				break ;
		}
	}
	
	private void scoring( int num )
	{
		score = score + num*10 ;
		mainFrame.displayScore( score ) ;
	}
	
	protected static void pressUpKey( ){
		int maxRelativeCoordX=0 ;
		
		for( int counter = 1 ; counter < 4 ; counter++ )
		{
			if( MainControl.relativeCoords.relativeCoordX[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] > maxRelativeCoordX )
				maxRelativeCoordX = MainControl.relativeCoords.relativeCoordX[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] ;			
		}
		
		if( ((MainControl.currentCoordX+maxRelativeCoordX+1) <= 11) )
		{
			if( (currentPiecesType != 0 )||( (currentPiecesState != 0 )&&( currentPiecesState !=2) ))
			{
				MainControl.clearBlocks( 1 ) ;
				MainControl.currentPiecesState = (MainControl.currentPiecesState+1)%4 ;
				
				if( (MainControl.crashCheck( )) == 1)
				{
					MainControl.currentPiecesState = (MainControl.currentPiecesState-1)%4 ;
					MainControl.setBlocks( ) ;	
				}
				else {
					MainControl.setBlocks( ) ;	
					mainFrame.drawGameState( ) ;
				}
			}
		}
	}
	
	protected static int downMove( ) {
		int maxRelativeCoordY = 0 ;
		
		MainControl.clearBlocks( 1 ) ;
		MainControl.currentCoordY ++ ;
		
		for( int counter = 1 ; counter < 4 ; counter++ )
		{
			if( MainControl.relativeCoords.relativeCoordY[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] > maxRelativeCoordY )
				maxRelativeCoordY = MainControl.relativeCoords.relativeCoordY[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] ;			
		}
		
		if( (( MainControl.currentCoordY+maxRelativeCoordY) > 25) || ( MainControl.crashCheck( ) == 1 ))
		{
			MainControl.currentCoordY -- ;
			MainControl.setBlocks( ) ;	
			return 1 ;
		}
		else{
			MainControl.setBlocks( ) ;	
			mainFrame.drawGameState( ) ;
			return 0 ;
		}
	}
	
	protected static void pressLeftKey() {

		if( ( MainControl.currentCoordX > -1 ) )
		{
			if( (currentPiecesType == 0 )&&( (currentPiecesState == 1 )||( currentPiecesState ==3) ) )
			{
				if( MainControl.currentCoordX > 0 )
				{
					MainControl.clearBlocks( 1 ) ;
					MainControl.currentCoordX -- ;
					if( ( MainControl.crashCheck() == 1 ) )
					{
						MainControl.currentCoordX++ ; 
					}
					MainControl.setBlocks( ) ;	
					mainFrame.drawGameState( ) ;	
				}
			}
			else {
				MainControl.clearBlocks( 1 ) ;
				MainControl.currentCoordX -- ;
				if( ( MainControl.crashCheck() == 1 ) )
				{
					MainControl.currentCoordX++ ; 
				}
				MainControl.setBlocks( ) ;	
				mainFrame.drawGameState( ) ;	
			}			
		}			
	}
		
	protected static void pressRightkey(){
		int maxRelativeCoordX=0 ;
			
		for( int counter = 1 ; counter < 4 ; counter++ )
		{
			if( MainControl.relativeCoords.relativeCoordX[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] > maxRelativeCoordX )
				maxRelativeCoordX = MainControl.relativeCoords.relativeCoordX[MainControl.currentPiecesType][MainControl.currentPiecesState][counter] ;			
		}
			
		if( ((MainControl.currentCoordX+maxRelativeCoordX+1) <= 11) )
		{
			MainControl.clearBlocks( 1 ) ;
			MainControl.currentCoordX ++ ;
			if( (MainControl.crashCheck() == 1) )
			{
				MainControl.currentCoordX -- ;	
				MainControl.setBlocks( ) ;	
			}
			else{
				MainControl.setBlocks( ) ;	
				mainFrame.drawGameState( ) ;
			}
		}
	}
}
