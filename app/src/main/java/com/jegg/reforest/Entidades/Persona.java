package com.jegg.reforest.Entidades;

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
    private String apellido;

    @DatabaseField(columnName = Constantes.FECHA_NACIMIENTO_PERSONAS)
    private Date fecha;

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
        this.apellido = apellido;
        this.fecha = fecha;
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
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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


    @Override
    public String toString() {
        String objetoJson = "{"+"'id':"+String.valueOf(id) +
                ", 'nombre':" + nombre +
                ", 'apellidos':" + apellido +
                ", 'fecha_nacimiento':" + fecha.toString() +
                ", 'genero':" + genero +
                ", 'direccion':" + direccion +
                ", 'telefono':" + telefono +
                ", 'municipio_id':" + String.valueOf(municipio.getId()) +
                "}";
        return super.toString();
    }
}
