package effects;

import java.io.Serializable;

import entity.Mob;

public interface Action extends Serializable{

	public void tick(Mob m);
	public void init(Mob m);
	public void end(Mob m);
	
}
