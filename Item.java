
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
    
    public Item(String descripcion , int peso) {
        this.descripcion = descripcion;
        this.peso = peso;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getPeso() {
        return peso;
    }
 
}
