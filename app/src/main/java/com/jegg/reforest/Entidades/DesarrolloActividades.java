package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.util.Date;

import static com.jegg.reforest.Utils.Constantes.sdf;

@DatabaseTable(tableName = Constantes.TABLA_DESARROLLO_ACTIVIDADES)
public class DesarrolloActividades {

    @DatabaseField(columnName = "iddesa", unique = true)
    private transient Integer id;

    @DatabaseField(generatedId = true, columnName = "id")
    private transient Integer iddesa;

    @DatabaseField(columnName = Constantes.FOTO_DESARROLLO_ACTIVIDADES)
    private String urlimagen;

    @DatabaseField(columnName = Constantes.COMENTARIO_DESARROLLO_ACTIVIDADES)
    private String comentario;

    @DatabaseField(columnName = Constantes.FECHA_DESARROLLO_ACTIVIDADES, canBeNull = false)
    private String fecha;

    @DatabaseField(columnName = Constantes.ACTIVIDAD_DESARROLLO_ACTIVIDADES)
    private int actividades_id;

    @DatabaseField(columnName = Constantes.ARBOL_DESARROLLO_ACTIVIDADES)
    private String arbol_id;

    @DatabaseField(columnName = Constantes.PERSONA_DESARROLLO_ACTIVIDADES)
    private int personas_id;

    @DatabaseField(columnName = Constantes.CREATED_AT)
    private String created_at;

    @DatabaseField(columnName = Constantes.UPDATED_AT)
    private String updated_at;

    @DatabaseField(columnName = Constantes.ACTIVIDAD_DESARROLLO_ACTIVIDADES + "2", foreign = true, foreignAutoRefresh = true)
    private transient Actividad idActividad;

    @DatabaseField(columnName = Constantes.ARBOL_DESARROLLO_ACTIVIDADES + "2", canBeNull = false, foreign = true)
    private transient Arbol arbol;

    @DatabaseField(columnName = Constantes.PERSONA_DESARROLLO_ACTIVIDADES + "2", foreign = true, foreignAutoRefresh = true)
    private transient Persona persona;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    public DesarrolloActividades(String urlFoto, String comentario, String fecha, Actividad idActividad, Arbol arbol, Persona persona) {
        this.urlimagen = "data:image/jpeg;base64," + urlFoto;
        this.comentario = comentario;
        this.fecha = fecha;
        this.idActividad = idActividad;
        this.arbol = arbol;
        this.persona = persona;
        this.created_at = sdf.format(new Date());
        this.updated_at = sdf.format(new Date());
        this.actividades_id = idActividad.getId();
        this.arbol_id = arbol.getId();
        this.personas_id = persona.getId();
    }

    public String getFecha() {
        return fecha;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public int getActividades_id() {
        return actividades_id;
    }

    public Integer getId() {
        return id;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public DesarrolloActividades() {

    }
}
