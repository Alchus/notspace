package core;

public class CastAbilityIntent extends Intent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218309170771200747L;
		
	private Ability ability;
	
	public CastAbilityIntent(Ability ability) {
		
		this.ability = ability;
		priority = 100;

		
		
	}
	
	public void update(Unit u){
		
		if (ability.sourceUnit == null) ability.sourceUnit = u;
		
		//if (System.currentTimeMillis() - age > (1000. * ability.channelTime)){
			ability.start();
			
			expired = true;
		//}else{
		//	u.velocity = new XYPair(0,0);
		//}
		
	}

}
