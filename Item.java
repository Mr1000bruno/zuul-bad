
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String descripcion;
    private int peso;
    private boolean sePuedeCoger;
    public Item(String descripcion , int peso , boolean sePuedeCoger) {
        this.descripcion = descripcion;
        this.peso = peso;
        this.sePuedeCoger = sePuedeCoger;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public boolean sePuedeCoger() {
        return sePuedeCoger;
    }
    
    public String toString() {
        return descripcion + " el cual tiene un peso de " + peso + "GR.";
    }
}
