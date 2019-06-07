
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
    private boolean puedeSerCogido;
    public Item(String descripcion , int peso , boolean puedeSerCogido) {
        this.descripcion = descripcion;
        this.peso = peso;
        this.puedeSerCogido = puedeSerCogido;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public boolean puedeSerCogido() {
        return puedeSerCogido;
    }
}
