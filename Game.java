import java.util.ArrayList;
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
    private ArrayList<Room> habitacionesPasadas;
    private int vecesEjecutadoSeguidasBack;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        habitacionesPasadas = new ArrayList<>();
        vecesEjecutadoSeguidasBack = 0;
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
        entrada.addItem("Broche de Escarabajo", 100);
        entrada.addItem("Columna de fuego", 1500);
        //Sotano
        sotano.setExit("south", entrada);
        sotano.addItem("Pistola", 498);
        //Jardin
        jardin.setExit("north", entrada);
        jardin.setExit("northWest", habitacion);
        jardin.addItem("Pluma de oro", 1000);
        jardin.addItem("Microscopio", 4500);
        // Cocina
        cocina.setExit("west", entrada);
        //Habitacion
        habitacion.setExit("north", bano);
        habitacion.setExit("east", entrada);
        habitacion.setExit("southEast", jardin);
        habitacion.addItem("Reloj de bolsillo", 25);

        //Baño
        bano.setExit("south", habitacion);
        bano.setExit("southEast", entrada);
        bano.addItem("Espejo de oro", 1500);
        bano.addItem("Horquilla de Jade", 4896);
        bano.addItem("Jabonera de oro", 4320);
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
            vecesEjecutadoSeguidasBack = 0;
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("back")) {
            if(vecesEjecutadoSeguidasBack < 2) {
                vecesEjecutadoSeguidasBack ++;
                backRoom();
            } else {
                System.out.println("Solo puede introducir el comando back dos veces seguidas");
            }
        }
        else if (commandWord.equals("eat")) {
            eat();
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
            habitacionesPasadas.add(previousRoom);
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
        if(habitacionesPasadas.size() > 0) {
            currentRoom = habitacionesPasadas.get(habitacionesPasadas.size() - 1);
            habitacionesPasadas.remove(habitacionesPasadas.size() - 1);
            printLocationInfo();
        } else {
            System.out.println("No puedes retroceder mas");
        }
    }
}
