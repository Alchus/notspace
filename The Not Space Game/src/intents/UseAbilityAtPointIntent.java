package intents;

import java.io.Serializable;

import core.Ability;
import core.Intent;
import core.XYPair;

public class UseAbilityAtPointIntent extends Intent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5448978257277250968L;

    public UseAbilityAtPointIntent(XYPair target, Ability spell) {
        priority = ABILITY;
    }

}
