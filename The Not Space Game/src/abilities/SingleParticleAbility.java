package abilities;

import java.util.ArrayList;

import core.Ability;
import core.Intent;
import core.Unit;
import core.Window;

public class SingleParticleAbility extends Ability{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8045655303741390922L;
	public Unit base_particle = null;
	
	
	
	public SingleParticleAbility(Unit particle) {
		this.base_particle = particle;
	}
	
	protected void payload(){
		
		Unit particle = base_particle.clone();
		particle.moveTo(sourceUnit.center());
		ArrayList<Intent> di = particleIntents();
		for (Intent i : di){
			particle.defaultIntents.add(i.clone());
		}
		Window.main.game.babies.add(particle);
	}
	
	public ArrayList<Intent> particleIntents(){
		return new ArrayList<Intent>();
	}
	
	

}
