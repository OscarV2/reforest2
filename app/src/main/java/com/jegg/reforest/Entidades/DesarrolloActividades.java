package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_DESARROLLO_ACTIVIDADES)
public class DesarrolloActividades {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_DESARROLLO_ACTIVIDADES)
    private int id;

    @DatabaseField(columnName = Constantes.FOTO_DESARROLLO_ACTIVIDADES)
    private String urlFoto;

    @DatabaseField(columnName = Constantes.COMENTARIO_DESARROLLO_ACTIVIDADES)
    private String comentario;

    @DatabaseField(columnName = Constantes.FECHA_DESARROLLO_ACTIVIDADES, canBeNull = false)
    private Date fecha;

    @DatabaseField(columnName = Constantes.ACTIVIDAD_DESARROLLO_ACTIVIDADES, foreign = true, foreignAutoRefresh = true)
    private Actividad idActividad;

    @DatabaseField(columnName = Constantes.ARBOL_DESARROLLO_ACTIVIDADES, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.PERSONA_DESARROLLO_ACTIVIDADES, foreign = true, foreignAutoRefresh = true)
    private Persona persona;

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty("id", id);
        objetoJson.addProperty(Constantes.FOTO_DESARROLLO_ACTIVIDADES, urlFoto);
        objetoJson.addProperty(Constantes.COMENTARIO_DESARROLLO_ACTIVIDADES, comentario);
        objetoJson.addProperty(Constantes.FECHA_DESARROLLO_ACTIVIDADES, fecha.toString());
        objetoJson.addProperty(Constantes.ACTIVIDAD_DESARROLLO_ACTIVIDADES, idActividad.getId());
        objetoJson.addProperty(Constantes.ARBOL_DESARROLLO_ACTIVIDADES, arbol.getId());
        objetoJson.addProperty(Constantes.PERSONA_DESARROLLO_ACTIVIDADES, persona.getId());

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }

    public DesarrolloActividades(String urlFoto, String comentario, Date fecha, Actividad idActividad, Arbol arbol, Persona persona) {
        this.urlFoto = urlFoto;
        this.comentario = comentario;
        this.fecha = fecha;
        this.idActividad = idActividad;
        this.arbol = arbol;
        this.persona = persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlFoto() {
        return urlFoto;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public DesarrolloActividades() {

    }
}
