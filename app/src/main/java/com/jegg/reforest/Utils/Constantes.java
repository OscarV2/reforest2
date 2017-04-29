package com.jegg.reforest.Utils;


public class Constantes {

    public static final String TABLA_ESTADO="estado";
    public static final String ID_ESTADO="_id";
    public static final String ESTADO_ESTADO="estado";

    public static final String TABLA_ARBOL_ESTADO="arbol_estado";
    public static final String ARBOL_ID="arbol_id";
    public static final String ID_ARBOL_ESTADO="_id";
    public static final String ESTADO_ARBOL_ESTADO="estado_id";

    public static final String TABLA_ESPECIE="especie";
    public static final String ID_ESPECIE="_id";
    public static final String ESPECIE_ESPECIE="especie";

    public static final String TABLA_ESPECIE_ARBOL="especie_arbol";
    public static final String ID_ESPECIE_ARBOL="_id";
    public static final String ESPECIE_ESPECIE_ARBOL="especie_id";
    public static final String ARBOL_ESPECIE_ARBOL="arbol_id";

    public static final String TABLA_ARBOL="arbol";
    public static final String ID_ARBOL="_id";
    public static final String LOTE_ID_ARBOL="lote_id";
    public static final String COORDENADAS_ARBOL="coordenadas";
    public static final String FECHA_ARBOL="fecha_id";

    public static final String TABLA_ALTURA="altura";
    public static final String ID_ALTURA="_id";
    public static final String ALTURA_ARBOL="arbol_id";
    public static final String MEDIDAS_ALTURA="medidas";

    public static final String TABLA_LOTE="lote";
    public static final String ID_LOTE="_id";
    public static final String NOMBRE_LOTE="nombre";
    public static final String FECHA_LOTE="fecha";
    public static final String AREA_LOTE="area";
    public static final String DELIMITACION="delimitacion";
    public static final String MUNICIPIO_LOTE="municipio_id";

    public static final String TABLA_DEPARTAMENTO="departamento";
    public static final String ID_DEPARTAMENTO="_id";
    public static final String NOMBRE_DEPARTAMENTO="nombre";

    public static final String TABLA_MUNICIPIO="municipio";
    public static final String ID_MUNICIPIO="_id";
    public static final String NOMBRE_MUNICIPIO="nombre";
    public static final String DEPARTAMENTO_MUNICIPIO="departamento_id";


    public static final String TABLA_PERSONAS="personas";
    public static final String ID_PERSONAS="_id";
    public static final String NOMBRE_PERSONAS="nombre";
    public static final String APELLIDO_PERSONAS="apellidos";
    public static final String FECHA_NACIMIENTO_PERSONAS="fecha_nacimiento";
    public static final String GENERO_PERSONAS="genero";
    public static final String DIRECCION_PERSONAS="direccion";
    public static final String TELEFONO_PERSONAS="telefono";
    public static final String ELIMINADO="eliminado";
    public static final String MUNICIPIO_PERSONAS="municipio_id";

    public static final String TABLA_USUARIO="users";
    public static final String ID_USUARIO="_id";
    public static final String NOMBRE_USUARIO="name";
    public static final String CORREO_USUARIO="email";
    public static final String USUARIO_PERSONA="persona_id";
    public static final String CLAVE_USUARIO="password";
    public static final String REMEMBER_TOKEN_USUARIO="remember_token";

    public static final String TABLA_ACTIVIDADES="actividades";
    public static final String ID_ACTIVIDADES="_id";
    public static final String NOMBRE_ACTIVIDADES="nombre";

    public static final String TABLA_DESARROLLO_ACTIVIDADES="desarrollo_actividades";
    public static final String ID_DESARROLLO_ACTIVIDADES="_id";
    public static final String FOTO_DESARROLLO_ACTIVIDADES="urlimagen";
    public static final String COMENTARIO_DESARROLLO_ACTIVIDADES="comentario";
    public static final String FECHA_DESARROLLO_ACTIVIDADES="fecha";
    public static final String ACTIVIDAD_DESARROLLO_ACTIVIDADES="actividades_id";
    public static final String ARBOL_DESARROLLO_ACTIVIDADES="arbol_id";
    public static final String PERSONA_DESARROLLO_ACTIVIDADES="personas_id";

    public static final String TABLA_MIGRATIONS="migrations";
    public static final String MIGRATION="migration";
    public static final String BATCH="batch";

    public static final String TABLA_PASSWORD_RESETS="password_resets";
    public static final String EMAIL="migration";
    public static final String TOKEN="batch";
    public static final String CREADO_PASSWORD_RESETS="created_at";
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

    //TABLA ARBOL ESTADO
    public static final String CREATE_TABLA_ARBOL_ESTADO="CREATE TABLE "+TABLA_ARBOL_ESTADO+" ("+
            ID_ARBOL_ESTADO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ESTADO_ARBOL_ESTADO+  " INT, " +
            ARBOL_ID+  " INT, " +
            "FOREIGN KEY ("+ARBOL_ID+") "+
            "REFERENCES "+ TABLA_ARBOL+"("+ID_ARBOL+") "+
            "FOREIGN KEY ("+ESTADO_ARBOL_ESTADO+") "+
            "REFERENCES "+ TABLA_ESTADO+"("+ID_ESTADO+") "+
            ");";

    //TABLA ALTURA
    public static final String CREATE_TABLA_ALTURA="CREATE TABLE "+TABLA_ALTURA+" ("+
            ID_ALTURA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ALTURA_ARBOL + " INTEGER, " +
            MEDIDAS_ALTURA+" TEXT, "+
            "FOREIGN KEY ("+ALTURA_ARBOL+") "+
            "REFERENCES "+ TABLA_ARBOL+"("+ID_ARBOL+") "+
               ");";

    //TABLA ESPECIE
    public static final String CREATE_TABLA_ESPECIE="CREATE TABLE "+TABLA_ESPECIE+" ("+
            ID_ESPECIE+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ESPECIE_ESPECIE+" TEXT );";

    //TABLA ESPECIE ARBOL
    public static final String CREATE_TABLA_ESPECIE_ARBOL="CREATE TABLE "+TABLA_ESPECIE_ARBOL+" ("+
            ID_ESPECIE_ARBOL+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ARBOL_ESPECIE_ARBOL+" TEXT," +
            ESPECIE_ESPECIE_ARBOL+" TEXT, "+

            "FOREIGN KEY ("+ESPECIE_ESPECIE_ARBOL+") "+
            "REFERENCES "+ TABLA_ESPECIE+"("+ID_ESPECIE+") " +
            "FOREIGN KEY ("+ARBOL_ESPECIE_ARBOL+") "+
            "REFERENCES "+ TABLA_ARBOL+"("+ID_ARBOL+") " +
            ");";

    //TABLA ARBOL
    public static final String CREATE_TABLA_ARBOL="CREATE TABLE "+TABLA_ARBOL+" ("+
            ID_ARBOL+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COORDENADAS_ARBOL+" TEXT, "+
            FECHA_ARBOL + " DATETIME, " +
            LOTE_ID_ARBOL + " INTEGER, " +
            "FOREIGN KEY ("+LOTE_ID_ARBOL+") "+
            "REFERENCES "+ TABLA_LOTE+"("+ID_LOTE+"));";


    //TABLA DEPARTAMENTO
    public static final String CREATE_TABLA_DEPARTAMENTO="CREATE TABLE "+TABLA_DEPARTAMENTO+" ("+
            ID_DEPARTAMENTO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOMBRE_DEPARTAMENTO+" TEXT);";
    //TABLA MUNICIPIO
    public static final String CREATE_TABLA_MUNICIPIO="CREATE TABLE "+TABLA_MUNICIPIO+" ("+
            ID_MUNICIPIO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOMBRE_MUNICIPIO+" TEXT, "     +
            DEPARTAMENTO_MUNICIPIO +" INT, " +
            "FOREIGN KEY ("+DEPARTAMENTO_MUNICIPIO+") "+
            "REFERENCES "+ TABLA_DEPARTAMENTO+"("+ID_DEPARTAMENTO+"));";

    //TABLA LOTE
    public static final String CREATE_TABLA_LOTE="CREATE TABLE "+TABLA_LOTE+" ("+
            ID_LOTE+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOMBRE_LOTE+" TEXT, "     +
            FECHA_LOTE +" DATETIME, " +
            AREA_LOTE + " DOUBLE(8,2), " +
            DELIMITACION + " TEXT, " +
            MUNICIPIO_LOTE + " INTEGER, " +
            "FOREIGN KEY ("+MUNICIPIO_LOTE+") "+
            "REFERENCES "+ TABLA_MUNICIPIO+"("+ID_MUNICIPIO+"));";

    //TABLA PERSONAS
    public static final String CREATE_TABLA_PERSONAS="CREATE TABLE "+TABLA_PERSONAS+" ("+
            ID_PERSONAS+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOMBRE_PERSONAS+" TEXT, " +
            APELLIDO_PERSONAS+" TEXT, " +
            FECHA_NACIMIENTO_PERSONAS +" DATETIME, " +
            GENERO_PERSONAS + " ENUM, " +
            DIRECCION_PERSONAS + " TEXT, " +
            TELEFONO_PERSONAS+ " TEXT, " +
            ELIMINADO + " INT, " +
            MUNICIPIO_PERSONAS+" INTEGER, " +
            "FOREIGN KEY ("+MUNICIPIO_PERSONAS+") "+
            "REFERENCES "+ TABLA_MUNICIPIO+"("+ID_MUNICIPIO+"));";

    //TABLA ACTIVIDADES
    public static final String CREATE_TABLA_ACTIVIDADES="CREATE TABLE "+TABLA_ACTIVIDADES+" ("+
            ID_ACTIVIDADES+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOMBRE_ACTIVIDADES+" TEXT);";

    //TABLA DESARROLLO ACTIVIDADES
    public static final String CREATE_TABLA_DESARROLLO_ACTIVIDADES="CREATE TABLE "+TABLA_DESARROLLO_ACTIVIDADES+" ("+
            ID_DESARROLLO_ACTIVIDADES+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FOTO_DESARROLLO_ACTIVIDADES + " TEXT, " +
            COMENTARIO_DESARROLLO_ACTIVIDADES + " TEXT, " +
            FECHA_DESARROLLO_ACTIVIDADES + " DATETIME, " +
            ACTIVIDAD_DESARROLLO_ACTIVIDADES+" INTEGER, " +
            ARBOL_DESARROLLO_ACTIVIDADES +" TEXT, " +
            PERSONA_DESARROLLO_ACTIVIDADES +" INTEGER, " +
            "FOREIGN KEY ("+ACTIVIDAD_DESARROLLO_ACTIVIDADES+") "+
            "REFERENCES "+ TABLA_ACTIVIDADES+"("+ID_ACTIVIDADES+") " +
            "FOREIGN KEY ("+ARBOL_DESARROLLO_ACTIVIDADES+") "+
            "REFERENCES "+ TABLA_ARBOL+"("+ID_ARBOL+") " +
            "FOREIGN KEY ("+PERSONA_DESARROLLO_ACTIVIDADES+") "+
            "REFERENCES "+ TABLA_PERSONAS+"("+ID_PERSONAS+"));";

    //TABLA USUARIO
    public static final String CREATE_TABLA_USUARIOS="CREATE TABLE "+TABLA_USUARIO+" ("+
            ID_USUARIO+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOMBRE_USUARIO + " TEXT, " +
            EMAIL + " TEXT, " +
            CLAVE_USUARIO + "TEXT, " +
            USUARIO_PERSONA +" INTEGER);";

}
























