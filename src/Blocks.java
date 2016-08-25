
public class Blocks {
	protected int existBlock ;
	protected int blockType ;
	
	public Blocks(){
		existBlock = 0 ;
		blockType = 0 ;
	}
	
	protected void setValue( int exist_block , int block_type) {
		existBlock = exist_block ;
		blockType = block_type ;
	}
}
