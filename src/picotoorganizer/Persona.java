/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picotoorganizer;

/**
 *
 * @author pablo
 */
class Persona {
    String nombre;

    public Persona(String nombre, String apellidos, String dni, String fecha_nacimiento, String fecha_fallecimiento, String genero, String email, int telefono, String direccion, String poblacion, String provincia, String region, String cod_postal, String pais, int estado_civil, String estado_laboral) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_fallecimiento = fecha_fallecimiento;
        this.genero = genero;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.region = region;
        this.cod_postal = cod_postal;
        this.pais = pais;
        this.estado_civil = estado_civil;
        this.estado_laboral = estado_laboral;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", fecha_nacimiento=" + fecha_nacimiento + ", fecha_fallecimiento=" + fecha_fallecimiento + ", genero=" + genero + ", email=" + email + ", telefono=" + telefono + ", direccion=" + direccion + ", poblacion=" + poblacion + ", provincia=" + provincia + ", region=" + region + ", cod_postal=" + cod_postal + ", pais=" + pais + ", estado_civil=" + estado_civil + ", estado_laboral=" + estado_laboral + '}';
    }
    String apellidos;
    String dni;
    String fecha_nacimiento;
    String fecha_fallecimiento;
    String genero;
    String email;
    int telefono;
    String direccion;
    String poblacion;
    String provincia;
    String region;
    String cod_postal;
    String pais;
    int estado_civil;
    String estado_laboral;
}
