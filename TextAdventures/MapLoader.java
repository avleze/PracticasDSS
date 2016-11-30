package TextAdventures;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 * MapLoader class
 *
 * @author juan
 */
public class MapLoader {

    //Otra opción
    /*PlayerCharacter player;
    public MapLoader(PlayerCharacter player){
        this.player = player;
    }*/
    Map loadFromFile(String file, PlayerCharacter player) {
        System.out.println("Entro en loadFromFile");
        Map map;
        int width = 0;
        int height = 0;
        Room[][] rooms = new Room[0][0];
        try {
            File filexml = new File(file);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filexml);

            doc.getDocumentElement().normalize();

            width = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            height = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
            rooms = new Room[width][height];

            NodeList nList = doc.getDocumentElement().getElementsByTagName("room");

            System.out.println("Voy a mirar los que tengan tag room: " + nList.getLength());

            for (int i = 0; i < nList.getLength(); ++i) {

                Node nRoom = nList.item(i);
                if (nRoom.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList nListChilds = nRoom.getChildNodes();
                    NamedNodeMap attributesForRoom = nRoom.getAttributes();
                    int row = Integer.parseInt(attributesForRoom.item(1).getNodeValue());
                    int col = Integer.parseInt(attributesForRoom.item(0).getNodeValue());

                    Message message = null;
                    Set<Action> actions = new HashSet<>();
                    Enemy enemy = null;

                    System.out.println("Tengo " + nListChilds.getLength() + "hijos");
                    for (int j = 0; j < nListChilds.getLength(); j++) {
                        Node nRoomChild = nListChilds.item(j);

                        if (nRoomChild.getNodeType() == Node.ELEMENT_NODE) {
                            NamedNodeMap atributtes;
                            System.out.println("Escojo el hijo: " + nRoomChild.getNodeName());
                            switch (nRoomChild.getNodeName()) {
                                case "message":
                                    atributtes = nRoomChild.getAttributes();
                                    String caption = atributtes.item(0).getNodeValue();
                                    String messages = atributtes.item(1).getNodeValue();
                                    System.out.println("Entro en mensajes");
                                    message = new Message(caption, messages);
                                    break;

                                case "actions":
                                    System.out.println("Entro en acciones");
                                    NodeList actionsNodes = nRoomChild.getChildNodes();

                                    Set<BattleAction> battleacts = new HashSet<>();
                                    Set<MovementAction> movacts = new HashSet<>();

                                    for (int m = 0; m < actionsNodes.getLength(); ++m) {
                                        Node actionChild = actionsNodes.item(m);
                                        if (actionChild.getNodeType() == Node.ELEMENT_NODE) {
                                            atributtes = actionChild.getAttributes();

                                            String description = atributtes.item(0).getNodeValue();
                                            System.out.println(actionChild.getNodeName() + " == BattleAction");
                                            if (actionChild.getNodeName().equals("BattleAction")) {

                                                BattleAction action = new BattleAction(description, player);
                                                battleacts.add(action);
                                            } else {
                                                MovementAction action = new MovementAction(description, player);
                                                movacts.add(action);
                                            }
                                        }
                                    }

                                    for (BattleAction it : battleacts) {
                                        actions.add((Action) it);
                                    }

                                    for (MovementAction it : movacts) {
                                        actions.add((Action) it);
                                    }
                                    break;

                                case "enemy":
                                    System.out.println("Entro en enemy");
                                    atributtes = nRoomChild.getAttributes();
                                    String name = atributtes.item(3).getNodeValue();
                                    int id = Integer.parseInt(atributtes.item(2).getNodeValue());
                                    int healthPoints = Integer.parseInt(atributtes.item(1).getNodeValue());
                                    int baseDamage = Integer.parseInt(atributtes.item(0).getNodeValue());

                                    NodeList itemsNodes = nRoomChild.getChildNodes();
                                    Set<Item> inventory = new HashSet<>();

                                    for (int k = 0; k < itemsNodes.getLength(); ++k) {
                                        Node inventoryChilds = itemsNodes.item(k);
                                        if (inventoryChilds.getNodeType() == Node.ELEMENT_NODE) {
                                            if (inventoryChilds.getNodeName().equals("pointsToHealth")) {
                                                RecoveryItem item = new RecoveryItem(Integer.parseInt(atributtes.item(0).getNodeValue()));
                                                inventory.add(item);
                                            } else {
                                                WeaponItem item = new WeaponItem(Integer.parseInt(atributtes.item(0).getNodeValue()));
                                                inventory.add(item);
                                            }

                                        }
                                        System.out.println(name);
                                        if (name.equals("Monster")) {
                                            enemy = new Monster(name, id, healthPoints, inventory, baseDamage);
                                        }
                                    }
                                    break;
                            }

                        }

                        Room room = new Room(message, actions, enemy);
                        rooms[row][col] = room;
                    }
                }

            }

        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }

        map = new Map(rooms, width, height);
        return map;
    }
}
