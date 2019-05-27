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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room southEastExit;
    private Room northWestExit;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west , Room southEast , Room  northWest) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        if(southEast != null)
            southEastExit = southEast;
        if(northWest != null)
            northWestExit = northWest;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion) {
        Room salida = null;
        if(direccion.equals("north")){
            salida = northExit;
        }
        if(direccion.equals("east")){
            salida = eastExit;
        }
        if(direccion.equals("south")){
            salida = southExit;
        }
        if(direccion.equals("west")){
            salida = westExit;
        }
        if(direccion.equals("southEast")){
            salida = southEastExit;
        }
        if(direccion.equals("northWest")){
            salida = northWestExit;
        }
        return salida;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        String salidaString = "Exits: ";
        if(northExit != null) {
            salidaString += " norte";
        }
        if(eastExit != null) {
            salidaString +=" este";
        }
        if(southExit != null) {
            salidaString +=" sur";
        }
        if(westExit != null) {
            salidaString +=" oeste";
        }
        if(southEastExit != null) {
            salidaString +=" sureste";
        }
        if(northWestExit != null) {
            salidaString +=" noroeste";
        }
        return salidaString;
    }
}
