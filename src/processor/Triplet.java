package processor;

/**
 * Created by Nishanth on 21/07/2016.
 */
public class Triplet {

	private Object c;
	private Object b;
	private Object a;

	public Triplet (Object a, Object b, Object c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Object getA() {
		return a;
	}

	public Object getB() {
		return b;
	}

	public Object getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return a.toString() + " | " + b.toString() + " | " + c;
	}
}
