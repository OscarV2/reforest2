package com.jegg.reforest.Utils;


public class Constantes {

    public static final String TABLA_ESTADO="estado";
    public static final String ID_ESTADO="_id";
    public static final String ESTADO_ESTADO="estado";

    public static final String TABLA_ARBOL_ESTADO="arbol_estado";
    public static final String ID_ARBOL_ESTADO="_id";
    public static final String ESTADO_ARBOL_ESTADO="estado_id";
    public static final String CAMBIO_ESTADO_FECHA_ARBOL_ESTADO="cambio_estado_fecha";

    public static final String TABLA_ESPECIE="especie";
    public static final String ID_ESPECIE="_id";
    public static final String ESPECIE_ESPECIE="especie";

    public static final String TABLA_ESPECIE_ARBOL="especie_arbol";
    public static final String ESPECIE_ESPECIE_ARBOL="especie_id";
    public static final String ARBOL_ESPECIE_ARBOL="arbol_id";
    public static final String CAMBIO_FECHA_ESPECIE_ARBOL="fecha_cambio";

    public static final String TABLA_ARBOL="arbol";
    public static final String ID_ARBOL="_id";
    public static final String LOTE_ID_ARBOL="lote_id";
    public static final String COORDENADAS_ARBOL="coordenadas";
    public static final String FECHA_ARBOL="fecha_id";
    public static final String ALTURA_ARBOL="altura_id";

    public static final String TABLA_ALTURA="altura";
    public static final String ID_ALTURA="_id";
    public static final String MEDIDAS_ALTURA="medidas";

    public static final String TABLA_FECHA="fecha";
    public static final String ID_FECHA="_id";
    public static final String FECHA_FECHA="fecha";

    public static final String TABLA_LOTE="lote";
    public static final String ID_LOTE="_id";
    public static final String NOMBRE_LOTE="nombre";
    public static final String FECHA_LOTE="fecha";
    public static final String AREA_LOTE="area";
    public static final String MUNICIPIO_LOTE="municipio_id";

    public static final String TABLA_DEPARTAMENTO="departamento";
    public static final String ID_DEPARTAMENTO="_id";
    public static final String NOMBRE_DEPARTAMENTO="nombre";

    public static final String TABLA_MUNICIPIO="municipio";
    public static final String ID_MUNICIPIO="_id";
    public static final String NOMBRE_MUNICIPIO="nombre";
    public static final String DEPARTAMENTO_MUNICIPIO="departamento_id";

    public static final String TABLA_ROLES="roles";
    public static final String ID_ROLES="_id";
    public static final String ROL_ROLES="rol";

    public static final String TABLA_PERSONAS="personas";
    public static final String ID_PERSONAS="_id";
    public static final String NOMBRE_PERSONAS="nombre";
    public static final String APELLIDO_PERSONAS="apellidos";
    public static final String FECHA_NACIMIENTO_PERSONAS="fecha_nacimiento";
    public static final String GENERO_PERSONAS="genero";
    public static final String DIRECCION_PERSONAS="direccion";
    public static final String TELEFONO_PERSONAS="telefono";
    public static final String MUNICIPIO_PERSONAS="municipio_id";

    public static final String TABLA_USUARIO="usuario";
    public static final String ID_USUARIO="_id";
    public static final String ROLES_ID_USUARIO="roles_id";
    public static final String PERSONAS_ID_USUARIO="personas_id";
    public static final String USUARIO_USUARIO="usuario";
    public static final String CLAVE_USUARIO="clave";

    public static final String TABLA_ACTIVIDADES="actividades";
    public static final String ID_ACTIVIDADES="_id";
    public static final String NOMBRE_ACTIVIDADES="nombre";

    public static final String TABLA_DESARROLLO_ACTIVIDADES="desarrollo_actividades";
    public static final String ID_DESARROLLO_ACTIVIDADES="_id";
    public static final String ACTIVIDAD_DESARROLLO_ACTIVIDADES="actividades_id";
    public static final String ARBOL_DESARROLLO_ACTIVIDADES="arbol_id";
    public static final String PERSONA_DESARROLLO_ACTIVIDADES="personas_id";
    public static final String FECHA_DESARROLLO_ACTIVIDADES="fecha";
    public static final String FOTO_DESARROLLO_ACTIVIDADES="foto";


    //crear tablas sql

    /*public static final String CREATE_TABLA_DETALLE_NOVEDADES="create table "+TABLA_DETALLE_NOVEDADES+" (" +
            ID_DETALLE_NOVEDADES+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DESCRIPCION_DETALLE_NOVEDADES+" TEXT, " +
            NOVEDAD_DETALLE_NOVEDADES+" INT, " +
            DISPOSITIVO_DETALLE_NOVEDADES+" TEXT, " +
            "FOREIGN KEY ("+NOVEDAD_DETALLE_NOVEDADES+") REFERENCES "+TABLA_NOVEDADES+" ("+ID_NOVEDADES+"));";
*/
    //TABLA ESTADO
    public static final String CREATE_TABLA_ESTADO="CREATE TABLE "+TABLA_ESTADO+" ("+
            ID_ESTADO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ESTADO_ESTADO+" TEXT );";

    //TABLA ALTURA
    public static final String CREATE_TABLA_ALTURA="CREATE TABLE "+TABLA_ALTURA+" ("+
            ID_ALTURA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MEDIDAS_ALTURA+" TEXT );";

    //TABLA ESPECIE
    public static final String CREATE_TABLA_ESPECIE="CREATE TABLE "+TABLA_ESPECIE+" ("+
            ID_ESPECIE+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ESPECIE_ESPECIE+" TEXT );";

}
