package abilities;

import intents.MoveAlongVectorIntent;

import java.io.Serializable;

import core.Ability;
import core.Window;

import units.Drone;

public class MysticShotGunAbility extends Ability implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1680458584403203262L;

    public MysticShotGunAbility() {
        energyCost = 103;

    }

    public void payload() {
        Drone particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 600;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint)));
        Window.main.game.babies.add(particle);

        particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 620;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint.sum(20, 0))));
        Window.main.game.babies.add(particle);
        particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 640;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint.sum(-10, 0))));
        Window.main.game.babies.add(particle);
        particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 660;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint.sum(0, 20))));
        Window.main.game.babies.add(particle);
        particle = new Drone(sourceUnit.center().x, sourceUnit.center().y, sourceUnit.owner);
        particle.movespeed = 680;
        particle.defaultIntents.clear();
        particle.defaultIntents.add(new MoveAlongVectorIntent(sourceUnit.center().vectorTo(targetPoint.sum(0, -10))));
        Window.main.game.babies.add(particle);

    }
}
