package com.jegg.reforest.Entidades;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;


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
    private transient ForeignCollection<Lote> lotes;

    @ForeignCollectionField
    private transient ForeignCollection<DesarrolloActividades> desarrolloActividades;

    @DatabaseField(columnName = "municipio_id")
    private int municipio_id;

    public String getClave() {
        return clave;
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

    public ForeignCollection<Lote> getLotes() {
        return lotes;
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
}
