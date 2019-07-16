package item;

public class IdCountPair {
	public String id;
	public int count;
	
	IdCountPair(String i, int c) {
		id = i;
		count = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IdCountPair) return id.equals(((IdCountPair) obj).id);
		return super.equals(obj);
	}
}
