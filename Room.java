import java.util.HashMap;
import java.util.Set;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private Item objeto;
    private HashMap<String , Room> salidas;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description , String descripcionObjeto , int pesoObjeto) 
    {
        this.description = description;
        objeto = new Item(descripcionObjeto , pesoObjeto);
        salidas = new HashMap<>();
    }

    /**
     * Define una salida para la habitacion.
     * @param direction El nombre de la direccion de la salida
     * @param neighbor La habitacion a la que se llega usando esa salida
     */
    public void setExit(String direccion, Room habitacion) {
        salidas.put(direccion, habitacion);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion) {
        return salidas.get(direccion);
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        Set<String> nombreDirecciones = salidas.keySet();
        String salidaString = "Salidas: ";
        for(String direccionActual : nombreDirecciones) {
            salidaString += direccionActual + " ";
        }
        return salidaString;
    }

    public String getObjectDescription() {
        String cadenaADevolver = "";
        cadenaADevolver += "En esta sala se encuentra el objeto " + objeto.getDescripcion() + "el cual tiene un peso de " + objeto.getPeso() + " gramos";
        return cadenaADevolver;
    }

    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription() {
        String cadenaADevolver = "Te encuentras en " + description + "\n" ;
        cadenaADevolver += getObjectDescription() + "\n";
        cadenaADevolver += getExitString();
        return cadenaADevolver;
    }
}
