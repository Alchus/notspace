package core;

import java.io.Serializable;

public class UseAbilityAtPointIntent extends Intent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5448978257277250968L;

    public UseAbilityAtPointIntent(XYPair target, Ability spell) {
        priority = ABILITY;
    }

}
