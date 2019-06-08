import java.util.ArrayList;
import java.util.Stack;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack habitacionesYaVisitadas;
    private ArrayList<Item> mochila;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        habitacionesYaVisitadas = new Stack();
        mochila = new ArrayList<>();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Adonde quieres ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("No ha puerta para salir");
        }
        else {
            Room previousRoom = currentRoom;
            habitacionesYaVisitadas.push(previousRoom);
            currentRoom = nextRoom;
            look();
        }
    }

    public void backRoom() {
        if(!habitacionesYaVisitadas.isEmpty()) {
            currentRoom = (Room) habitacionesYaVisitadas.pop();
            look();
        } else {
            System.out.println("No puedes retroceder mas");
        }
    }

    public void takeObject(Command objetoACoger) {
        if(!objetoACoger.hasSecondWord()) {
            System.out.println("¿Que objeto quiere coger?");
            return;
        }

        String nombreObjeto = objetoACoger.getSecondWord();

        if(currentRoom.buscarObjeto(nombreObjeto) != null) {
            Item objeto = currentRoom.buscarObjeto(nombreObjeto);
            currentRoom.eliminarObjetoDeSala(nombreObjeto);
            mochila.add(objeto);
            System.out.println("Se ha cogido el objeto " + nombreObjeto + ".");
        } else {
            System.out.println("No se ha encontrado el objeto " + nombreObjeto + " en la sala en la que se encuentra");
        }
    }

    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("Acabas de comer y ya no tienes hambre");
    }
}

