package core;

import intents.IdleIntent;

import java.io.Serializable;

public class Intent implements Comparable<Intent>, Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7512336422603391364L;
	public boolean removesLowerPrority = false;
	public boolean removesEqualOrLowerPrority = false;
	public int priority = 0;
	public long age; 
	
	//Priority Constants
	public static final int MOVEMENT = 10;
	public static final int ABILITY = 20;
	public static final int CHANNEL = 30;
	public static final int STUN = 40;
	public static final int CLEANSE = 100;
	public boolean expired = false;
	public static final Intent IDLE = new IdleIntent();

	public Intent(){
		age = System.currentTimeMillis();
	}
	
	public   void update(Unit u){
		
	}
	
	public int compareTo(Intent a){
		if (a.priority == priority) return (int)Math.signum(age - a.age) * -1;
		return  -1 *(priority - a.priority);
	}
	
	public Intent clone(){
		try {
				
			return (Intent)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
}
