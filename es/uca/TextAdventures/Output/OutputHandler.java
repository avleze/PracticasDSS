package es.uca.TextAdventures.Output;

import java.util.Set;

import es.uca.TextAdventures.Player.PlayerCharacter;
import es.uca.TextAdventures.Room;
import es.uca.TextAdventures.Action.Action;

/**
 * OutputHandler
 *
 * @author Antonio Vélez Estévez
 */
public interface OutputHandler {
    void showGameInformation(PlayerCharacter playerCharacter, Room room, Set<Action> actions);

    void showWelcomeScreen(PlayerCharacter playerCharacter);

    void showGameOverScreen(PlayerCharacter playerCharacter);

    void showWinnerScreen(PlayerCharacter playerCharacter);
}