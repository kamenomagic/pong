package ga;

public class Tuple<F, S>
{

private F f;
private S s;

	public Tuple(F f, S s)
	{
	    this.f = f;
	    this.s = s;
	}
	
	public F getFirst() {
		return this.f;
	}
	public S getSecond() {
		return this.s;
	}
}