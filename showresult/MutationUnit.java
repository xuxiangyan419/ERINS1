package showresult;

public class MutationUnit {
	public int pos;
	public int size;
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public MutationUnit(int pos, int size) {
		super();
		this.pos = pos;
		this.size = size;
	}
	
}
