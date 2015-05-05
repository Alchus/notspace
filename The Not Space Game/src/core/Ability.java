package core;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ability implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 1878683719685131753L;
    public XYPair targetPoint = null;
    public Unit targetUnit = null;
    public Unit sourceUnit = null;
    public double energyCost = 0.0;
    public double channelTime;
    public long age;
    public int cooldown = 0; //in game milliseconds
    public long lastCast = 0;

    public Ability() {
        age = System.currentTimeMillis();
    }

    public final void forceStart() {
        System.out.println("Force starting ability: " + this.getClass().toString());
        payload();
    }

    public final boolean start() { //returns if the cast was successful
        if (sourceUnit == null) {
            return false;
        }
        if (sourceUnit.energy < energyCost) {
            return false;
        }
        if (lastCast + cooldown > System.currentTimeMillis()) {
            return false;
        }
        payload();
        lastCast = System.currentTimeMillis();
        sourceUnit.energy -= energyCost;
        return true;

    }

    protected void payload() {
        System.out.println("A " + this.getClass().toString() + " ability didn't overload payload().");
    }
    
    public Ability clone(){
        Ability copy = null;
        try {
            copy = (Ability)super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ability.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (targetPoint != null){
        copy.targetPoint = this.targetPoint.clone();
        }
        
        copy.sourceUnit = null;
        
        return copy;
    }

}
