package com.jegg.reforest.Utils;

import android.os.Build;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;

public class Constantes {

    public static final String BASE_URL = "http://appreforest.com/servicios/";

    public static final String URL_SITIO_WEB = "http://appreforest.com";
    public static final String GET_PERSONAS = "personas/";

    public static final String POST_ARBOL = "arbol";

    public static final String POST_DESARROLLO_ACTIVIDAD = "desarrolloactividades";

    public static final String POST_ESPECIE = "especie";
    public static final String POST_ESPECIE_ARBOL = "arbolespecie";


    public static final String UPLOADED = "uploaded";
    public static final String POST_ALTURA = "altura";

    public static final String POST_ESTADO_ARBOL = "arbolestado";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String SERIAL = Build.SERIAL;

    public static final SecureRandom secureRandom = new SecureRandom();
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
    public static final String COORDENADAS_ARBOL="coodenadas";
    public static final String FECHA_ARBOL="fecha_arbol";

    public static final String TABLA_ALTURA="altura";
    public static final String ID_ALTURA="_id";
    public static final String ALTURA_ARBOL="arbol_id";
    public static final String MEDIDAS_ALTURA="medidas";

    public static final String TABLA_LOTE="lote";
    public static final String ID_LOTE="id";
    public static final String NOMBRE_LOTE="nombre";
    public static final String FECHA_LOTE="fecha_creacion";
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
    public static final String MUNICIPIO_PERSONAS="municipio_id";


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

}