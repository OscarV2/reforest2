package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_DESARROLLO_ACTIVIDADES)
public class DesarrolloActividades {

    @DatabaseField(generatedId = true, columnName = "id")
    private transient int id;

    @DatabaseField(columnName = Constantes.FOTO_DESARROLLO_ACTIVIDADES)
    private String urlFoto;

    @DatabaseField(columnName = Constantes.COMENTARIO_DESARROLLO_ACTIVIDADES)
    private String comentario;

    @DatabaseField(columnName = Constantes.FECHA_DESARROLLO_ACTIVIDADES, canBeNull = false)
    private String fecha;

    @DatabaseField(columnName = Constantes.ACTIVIDAD_DESARROLLO_ACTIVIDADES, foreign = true, foreignAutoRefresh = true)
    private Actividad idActividad;

    @DatabaseField(columnName = Constantes.ARBOL_DESARROLLO_ACTIVIDADES, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.PERSONA_DESARROLLO_ACTIVIDADES, foreign = true, foreignAutoRefresh = true)
    private Persona persona;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    public DesarrolloActividades(String urlFoto, String comentario, String fecha, Actividad idActividad, Arbol arbol, Persona persona) {
        this.urlFoto = urlFoto;
        this.comentario = comentario;
        this.fecha = fecha;
        this.idActividad = idActividad;
        this.arbol = arbol;
        this.persona = persona;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getUrlFoto() {
        return "data:image/png;base64," +  urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public Persona getPersona() {
        return persona;
    }

    public int getId() {
        return id;
    }

    public DesarrolloActividades() {

    }
}
