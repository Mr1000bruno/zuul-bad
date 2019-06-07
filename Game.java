import java.util.ArrayList;
import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack habitacionesYaVsitadas;
    private ArrayList<Item> mochila;
    private int pesoMochila;
    private static final int PESO_MAXIMO_MOCHILA = 7000;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        habitacionesYaVsitadas = new Stack();
        mochila = new ArrayList<>();
        pesoMochila = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, sotano, jardin, cocina, habitacion, bano;

        // create the rooms
        entrada = new Room("entrada principal de la casa");
        sotano = new Room("sotano");
        jardin = new Room("jardin con flores");
        cocina = new Room("cocina con muebles muy bonitos");
        habitacion = new Room("habitacion del dueno de la casa");
        bano = new Room("bano para el dueno de la casa");
        // initialise room exits
        // Entrada
        entrada.setExit("north", sotano);
        entrada.setExit("east", cocina);
        entrada.setExit("south", jardin);
        entrada.setExit("west", habitacion);
        entrada.setExit("northWest", bano);
        entrada.addItem("Broche", 100 , true);
        entrada.addItem("Columna", 1500 , false);
        //Sotano
        sotano.setExit("south", entrada);
        sotano.addItem("Pistola", 498 , true);
        //Jardin
        jardin.setExit("north", entrada);
        jardin.setExit("northWest", habitacion);
        jardin.addItem("Pluma", 1000 , true);
        jardin.addItem("Microscopio", 4500 , true);
        // Cocina
        cocina.setExit("west", entrada);
        //Habitacion
        habitacion.setExit("north", bano);
        habitacion.setExit("east", entrada);
        habitacion.setExit("southEast", jardin);
        habitacion.addItem("Reloj", 25 , true);

        //Baño
        bano.setExit("south", habitacion);
        bano.setExit("southEast", entrada);
        bano.addItem("Espejo", 1500 , false);
        bano.addItem("Horquilla", 4896 , true);
        bano.addItem("Jabonera", 4320 , true);
        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar.Hasta la proxima");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al Cluedo");
        System.out.println("En este fantastico juego va a tener que descubrir donde se encuentar el cadaver");
        System.out.println("Teclea 'help' si necesitas ayuda.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("No se lo que quiere decir");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("back")) {
            backRoom();          
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("items")) {
            showItems();
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("take")) {
            takeObject(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } 

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Eres un detective.");
        System.out.println("Tu mision es encontrar en que habitacion de la casa se encuentra el cadaver");
        System.out.println();
        System.out.println("La lista de comandos es la siguiente: ");
        parser.mostrarComandos();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            habitacionesYaVsitadas.push(previousRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat() {
        System.out.println("Acabas de comer y ya no tienes hambre");
    }

    private void backRoom() {
        if(!habitacionesYaVsitadas.isEmpty()) {
            currentRoom = (Room) habitacionesYaVsitadas.pop();
            printLocationInfo();
        } else {
            System.out.println("No puedes retroceder mas");
        }
    }

    private void takeObject(Command objetoACoger) {
        if(!objetoACoger.hasSecondWord()) {
            System.out.println("¿Que objeto quiere coger?");
            return;
        }

        String nombreObjeto = objetoACoger.getSecondWord();
        if(currentRoom.encontrarObjeto(nombreObjeto) != null) {
            Item objetoMochila = currentRoom.encontrarObjeto(nombreObjeto);
            if(objetoMochila.puedeSerCogido()) {
                pesoMochila += objetoMochila.getPeso();
                if(pesoMochila <= PESO_MAXIMO_MOCHILA) {
                    mochila.add(objetoMochila);
                    currentRoom.eliminarObjetoSala(nombreObjeto);
                    System.out.println("Se ha añadido el objeto " + nombreObjeto + " a la mochila");
                } else {
                    pesoMochila -= objetoMochila.getPeso();
                    System.out.println("El peso de la mochia a superado los " + PESO_MAXIMO_MOCHILA + "GR.");
                }     
            } else {
                System.out.println("No puede coger el objeto " + objetoMochila.getDescripcion() + " porque no esta permitido");
            }
        } else {
            System.out.println("No se encuentra el objeto " + nombreObjeto + " en esta sala.");
        }

    }

    private void dropItem(Command objetoAPosar) {
        if(!objetoAPosar.hasSecondWord()) {
            System.out.println("¿Que objeto quiere coger?");
            return;
        }        
        String nombreObjeto = objetoAPosar.getSecondWord();
        int contador = 0;
        boolean encontrado = false;
        while(contador < mochila.size() && !encontrado) {
            if(mochila.get(contador).getDescripcion().equalsIgnoreCase(nombreObjeto)) {
                String descripcionObjeto = mochila.get(contador).getDescripcion();
                int pesoObjeto = mochila.get(contador).getPeso();
                boolean puedeSerCogido = mochila.get(contador).puedeSerCogido();
                pesoMochila -= pesoObjeto;
                System.out.println("Se ha posado el objeto: " + mochila.get(contador).getDescripcion());
                mochila.remove(contador);
                currentRoom.addItem(descripcionObjeto, pesoObjeto, puedeSerCogido);
                encontrado = true;
            }
            contador ++;
        }
        if(!encontrado) {
            System.out.println("La mochila no contiene el objeto " + nombreObjeto);
        }
    }

    private void showItems() {
        String cadenaADevolver = "";
        if(!mochila.isEmpty()) { 
            cadenaADevolver += "Lleva en la mochila: ";
            for (Item itemActual : mochila) {
                cadenaADevolver +=  itemActual.getDescripcion() + ","; 
            }
            cadenaADevolver = cadenaADevolver.substring(0, cadenaADevolver.length() - 1) + "\n";
            cadenaADevolver += "La mochila tiene un peso de " + pesoMochila + "GR.";
        } else {
            cadenaADevolver = "No tiene ningun objeto en la mochila.";
        }
        System.out.println(cadenaADevolver);
    }
}
