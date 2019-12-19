package effects;

import java.io.Serializable;

import entity.Entity;
import entity.Mob;

public interface OnHit extends Serializable {
	public void hit(Entity source, Mob target);
}
