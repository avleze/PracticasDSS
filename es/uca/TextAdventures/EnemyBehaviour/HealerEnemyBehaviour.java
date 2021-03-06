package es.uca.TextAdventures.EnemyBehaviour;

import es.uca.TextAdventures.Action.Action;
import es.uca.TextAdventures.Action.BattleAction;
import es.uca.TextAdventures.Action.HealAction;

import java.util.Set;

/**
 * Created by manuelrdsg on 4/12/16.
 */
public class HealerEnemyBehaviour extends EnemyBehaviour {

    public HealerEnemyBehaviour(Set<BattleAction> actions) {
        super(actions);
    }

    @Override
    public Action getAction() {
        return this.actions.stream().filter((i) -> i instanceof HealAction).iterator().next();
    }
}
