package abilities;

import intents.MoveAlongVectorIntent;
import core.Ability;
import core.Window;
import units.Drone;

public class TestBasicAttackAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = -1768098047623210976L;

    public TestBasicAttackAbility() {

        cooldown = 200;

    }

    public void payload() {
        Drone particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 500;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint)));
        Window.main.game.babies.add(particle);
    }

}
