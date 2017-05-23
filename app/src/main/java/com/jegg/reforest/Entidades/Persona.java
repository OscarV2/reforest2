package com.jegg.reforest.Entidades;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;

@DatabaseTable(tableName = Constantes.TABLA_PERSONAS)
public class Persona {

    @DatabaseField(id = true, columnName = Constantes.ID_PERSONAS)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_PERSONAS, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.APELLIDO_PERSONAS, canBeNull = false)
    private String apellidos;

    @DatabaseField(columnName = Constantes.FECHA_NACIMIENTO_PERSONAS)
    private String fecha_nacimiento;

    @DatabaseField(columnName = Constantes.GENERO_PERSONAS)
    private String genero;

    @DatabaseField(columnName = Constantes.DIRECCION_PERSONAS)
    private String direccion;

    @DatabaseField(columnName = Constantes.TELEFONO_PERSONAS)
    private String telefono;

    @DatabaseField(columnName = "correo")
    private String correo;

    @DatabaseField(columnName = "clave")
    private String clave;


    @ForeignCollectionField
    private transient ForeignCollection<DesarrolloActividades> desarrolloActividades;

    @DatabaseField(columnName = "municipio_id")
    private int municipio_id;

    public int getMunicipio_id() {
        return municipio_id;
    }

    public void setMunicipio_id(int municipio_id) {
        this.municipio_id = municipio_id;
    }
/*
    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty("id", id);
        objetoJson.addProperty(Constantes.NOMBRE_PERSONAS, nombre);
        objetoJson.addProperty(Constantes.APELLIDO_PERSONAS, apellidos);
        objetoJson.addProperty(Constantes.FECHA_NACIMIENTO_PERSONAS, fecha_nacimiento.toString());
        objetoJson.addProperty(Constantes.GENERO_PERSONAS, genero);
        objetoJson.addProperty(Constantes.DIRECCION_PERSONAS, direccion);
        objetoJson.addProperty(Constantes.TELEFONO_PERSONAS, telefono);
        objetoJson.addProperty(Constantes.MUNICIPIO_PERSONAS, municipio_id);
        objetoJson.addProperty("correo", correo);
        objetoJson.addProperty("clave", clave);

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }
*/
    public ForeignCollection<DesarrolloActividades> getDesarrolloActividades() {
        return desarrolloActividades;
    }

    public void setDesarrolloActividades(ForeignCollection<DesarrolloActividades> desarrolloActividades) {
        this.desarrolloActividades = desarrolloActividades;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Persona() {
    }

    public Persona(String nombre, String apellido, String fecha, String direccion, String telefono, String genero) {

        this.nombre = nombre;
        this.apellidos = apellido;
        this.fecha_nacimiento = fecha;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
