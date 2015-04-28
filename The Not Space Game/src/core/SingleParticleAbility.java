package core;

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
		Window.main.game.babies.add(particle);
	}
	
	
	

}
