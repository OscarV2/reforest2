package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_PERSONAS)
public class Persona {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_PERSONAS)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_PERSONAS, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.APELLIDO_PERSONAS, canBeNull = false)
    private String apellidos;

    @DatabaseField(columnName = Constantes.FECHA_NACIMIENTO_PERSONAS)
    private Date fecha_nacimiento;

    @DatabaseField(columnName = Constantes.GENERO_PERSONAS)
    private String genero;

    @DatabaseField(columnName = Constantes.DIRECCION_PERSONAS)
    private String direccion;

    @DatabaseField(columnName = Constantes.TELEFONO_PERSONAS)
    private String telefono;

    @DatabaseField(columnName = Constantes.MUNICIPIO_PERSONAS, foreign = true, foreignAutoRefresh = true)
    private Municipio municipio;

    @ForeignCollectionField
    private ForeignCollection<DesarrolloActividades> desarrolloActividades;

    private int municipio_id;

    public int getMunicipio_id() {
        return municipio_id;
    }

    public void setMunicipio_id(int municipio_id) {
        this.municipio_id = municipio_id;
    }

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
        objetoJson.addProperty(Constantes.MUNICIPIO_PERSONAS, municipio.getId());

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }

    public ForeignCollection<DesarrolloActividades> getDesarrolloActividades() {
        return desarrolloActividades;
    }

    public void setDesarrolloActividades(ForeignCollection<DesarrolloActividades> desarrolloActividades) {
        this.desarrolloActividades = desarrolloActividades;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Persona() {
    }

    public Persona(String nombre, String apellido, Date fecha, String direccion, String telefono, String genero) {

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

    public String getApellido() {
        return apellidos;
    }

    public void setApellido(String apellido) {
        this.apellidos = apellido;
    }

    public Date getFecha() {
        return fecha_nacimiento;
    }

    public void setFecha(Date fecha) {
        this.fecha_nacimiento = fecha;
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
