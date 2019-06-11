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
    private int pesoMaximoJugador;
    private int pesoMochila;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom , int pesoMaximoJugador) {
        this.currentRoom = currentRoom;
        habitacionesYaVisitadas = new Stack();
        mochila = new ArrayList<>();
        this.pesoMaximoJugador = pesoMaximoJugador;
        pesoMochila = 0;
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
            if(objeto.sePuedeCoger()) {
                if((pesoMochila + objeto.getPeso()) <= pesoMaximoJugador) {
                    pesoMochila += objeto.getPeso();
                    currentRoom.eliminarObjetoDeSala(nombreObjeto);
                    mochila.add(objeto);
                    System.out.println("Se ha cogido el objeto " + nombreObjeto + ".");
                } else {
                    System.out.println("No se ha podido coger el objeto porque su personaje no puede llevar mas de " + pesoMaximoJugador + "GR.");
                }
            } else {
                System.out.println("No esta permitido coger el objeto " + nombreObjeto + ".");
            }
        } else {
            System.out.println("No se ha encontrado el objeto " + nombreObjeto + " en la sala en la que se encuentra");
        }
    }

    public void dropObject(Command objetoASoltar){
        if(!objetoASoltar.hasSecondWord()) {
            System.out.println("¿Que objeto desea posar?");
            return;
        }

        String nombreObjeto = objetoASoltar.getSecondWord();

        int contador = 0;
        boolean encontrado = false;
        while (contador < mochila.size() && !encontrado) {
            if(mochila.get(contador).getDescripcion().equalsIgnoreCase(nombreObjeto)){
                String descripcionObjeto = mochila.get(contador).getDescripcion();
                int pesoObjeto = mochila.get(contador).getPeso();
                currentRoom.addItem(descripcionObjeto, pesoObjeto, true);
                pesoMochila -= pesoObjeto;
                mochila.remove(contador);
                System.out.println("Ha dejado en la sala el objeto " + nombreObjeto + ".");
                encontrado = true;
            }
            contador ++;
        }
        if(!encontrado) {
            System.out.println("No lleva el la mochila el objeto " + nombreObjeto + ".");
        }
    }

    public void showItems() {
        String cadenaADevolver = "";
        if(!mochila.isEmpty()) {
            cadenaADevolver = "Llevas en la mochila los siguientes objetos:\n";
            for (Item itemActual : mochila) {
                cadenaADevolver += itemActual + "\n";
            }
            cadenaADevolver += "El peso maximo que puede llevar el jugador es: " + pesoMaximoJugador + "GR.\n";
            cadenaADevolver += "La mochila tiene un peso de " + pesoMochila + "GR.";
        } else {
            cadenaADevolver = "No lleva ningun objeto en la mochila.";
        }
        System.out.println(cadenaADevolver);
    }
    
    public void aumentWeight() {
        if(currentRoom.buscarObjetoEspecial() != null){
            pesoMaximoJugador *= 2;
            pesoMochila = pesoMaximoJugador - pesoMochila;
            currentRoom.eliminarObjetoEspecial();
            System.out.println("HA COGIDO EL OBJETO ESPECIAL AHORA LA CAPACIDAD DE SU MOCHILA ES " + pesoMochila + "GR.");
        } else {
            System.out.println("El objeto especial no se encuentra en esta sala");
        }
    }

    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("Acabas de comer y ya no tienes hambre");
    }
}