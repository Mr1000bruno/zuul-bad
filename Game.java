import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
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
    private Player jugador;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        jugador = new Player(createRooms());
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room entrada, sotano, jardin, cocina, habitacion, bano;
        Room habitacionInicial;
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
        jardin.addItem("Microscopio", 4500 , false);
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
        // Creamos un array de String con el nombre de las habitaciones
        String [] habitaciones = {"entrada","sotano","jardin","cocina" , "habitacion" , "bano"};
        //Elegimos una al azar
        String habitacionAleatoria = habitaciones[0 + new Random().nextInt(5)];
        // Dependiendo que habitacion toque agregamos el objeto especial a dicha habitacion
        switch(habitacionAleatoria) {
            case "entrada": entrada.addItem("Mochila extra", 0, true);
            break;
            case "sotano" : sotano.addItem("Mochila extra", 0, true);
            break;
            case "jardin": jardin.addItem("Mochila extra", 0, true);
            break;
            case "cocina" : cocina.addItem("Mochila extra", 0, true);
            break;
            case "habitacion": habitacion.addItem("Mochila extra", 0, true);
            break;
            default : bano.addItem("Mochila extra", 0, true);
            break;
        }
        habitacionInicial = entrada;  // start game outside
        return habitacionInicial;
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
        jugador.look();
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
            jugador.goRoom(command);
        }
        else if (commandWord.equals("look")) {
            jugador.look();
        }
        else if (commandWord.equals("back")) {
            jugador.backRoom();          
        }
        else if (commandWord.equals("take")) {
            jugador.takeObject(command);
        }
        else if(commandWord.equals("drop")) {
            jugador.dropObject(command);
        }
        else if (commandWord.equals("items")){
            jugador.showItems();
        }
        else if(commandWord.equals("aumentWeight")){
            jugador.aumentWeight();
        }
        else if (commandWord.equals("eat")) {
            jugador.eat();
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
}
