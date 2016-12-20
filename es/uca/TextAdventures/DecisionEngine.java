package es.uca.TextAdventures;

import es.uca.TextAdventures.Action.*;
import es.uca.TextAdventures.Input.ConsoleInput;
import es.uca.TextAdventures.Input.InputManager;
import es.uca.TextAdventures.Item.WeaponItem;
import es.uca.TextAdventures.Output.*;
import es.uca.TextAdventures.Player.Enemy;
import es.uca.TextAdventures.Player.PlayerCharacter;
import java.util.HashSet;
import java.util.Set;

/**
 * DecisionEngine Class
 *
 * @author Juan Antonio Rodicio López
 */
public class DecisionEngine {

    PlayerCharacter playerCharacter;
    Map map;
    ConsoleOutput consoleOut;
    OutputManager output;
    InputManager input;
    ConsoleInput consoleIn;
    MapLoader mapLoader;
    ActionParameter actionParameters;
    Set<Action> playerActions;

    DecisionEngine(PlayerCharacter playerCharacter) throws WeaponItem.TypeNotFoundException, Enemy.TypeNotFoundException {
        this.playerCharacter = playerCharacter;
        this.mapLoader = new MapLoader(this.playerCharacter);

        this.consoleIn = new ConsoleInput();

        this.input = new InputManager(consoleIn);

        this.map = mapLoader.loadFromFile("map.xml");
        this.consoleOut = new ConsoleOutput();

    }

    void run() {

        boolean gameOver = false;
        int menuOption;

        output = new OutputManager(consoleOut, null, playerCharacter);

        do {
            output.showMessage("Text Adventures");
            output.showMessage("1. Load game");
            output.showMessage("2. New game (unimplemented yet)");
            output.showMessage("3. Credits (unimplemented yet)");
            menuOption = input.getInput();
        } while (menuOption < 1 || menuOption > 3);

        switch(menuOption) {
            case 1: {
                while (!gameOver) {

                    if (playerCharacter.getXPosition() == 1 && playerCharacter.getYPosition() == 1) {
                        gameOver = true;
                        output.showWinnerScreen();
                    }

                    Room room = map.getRoom(playerCharacter.getXPosition(),
                            playerCharacter.getYPosition());

                    playerActions = new HashSet<>();
                    playerActions.add(new Heal("Curarse.", playerCharacter));
                    playerActions.add(new RunAway("Huir.", playerCharacter));
                    playerActions.add(new Attack("Atacar.", playerCharacter, room.getEnemy()));

                    actionParameters = new ActionParameter(output, input, playerActions, playerCharacter, room.getEnemy());

                    output.setCurrentRoom(room);

                    output.show();
                    Action selectedAction = room.getAction(input.getInput() - 1);

                    selectedAction.run(actionParameters);

                    if (!playerCharacter.isAlive()) {

                        gameOver = true;
                        output.showGameOverScreen();

                    }

                }

                break;
            }

            case 2: {
                // Needs to be implemented
                break;
            }

            case 3: {
                // Credits
                break;
            }
        }

    }
}
