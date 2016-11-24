package TextAdventures;

/**
 * DecisionEngine Class
 * @author Juan Antonio Rodicio López
 */
public class DecisionEngine {
    
    PlayerCharacter playerCharacter;
    Map map;
    OutputManager output;
    InputManager input;
    
    DecisionEngine(PlayerCharacter playerCharacter, Map map){
        this.playerCharacter = playerCharacter;
        this.map = map;
    }
    
    void run(){
        
        boolean gameOver = false;
        
        while(!gameOver){
        
        Room room = map.getRoom(playerCharacter.getXPosition(), 
                                    playerCharacter.getYPosition);
        
        output.show(playerCharacter,room,room.getActions());
        
        room.getAction(input.getInput()).run();
        
            if(!playerCharacter.isAlive()){

                gameOver = true;
                input.showGameOverScreen(playerCharacter);

            } else if(/* Player reach the treasure room*/){

                gameOver = true;
                input.showWinnerScreen(playerCharacter);
            
            }
        
        }
        
    }
}
