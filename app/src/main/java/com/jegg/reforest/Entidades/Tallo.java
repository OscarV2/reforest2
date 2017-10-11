package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.util.Date;

import static com.jegg.reforest.Utils.Constantes.sdf;

@DatabaseTable(tableName = "tallos")
public class Tallo  {

    @DatabaseField(columnName = "ida", unique = true)
    private transient Integer id;

    @DatabaseField(generatedId = true, columnName = "id")
    private transient Integer idA;

    @DatabaseField(columnName = Constantes.ALTURA_ARBOL, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.ALTURA_ARBOL + "2")
    private String arbol_id;

    @DatabaseField(columnName = Constantes.MEDIDAS_ALTURA, canBeNull = false)
    private String medida;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    @DatabaseField(columnName = Constantes.CREATED_AT)
    private String created_at;

    @DatabaseField(columnName = Constantes.UPDATED_AT)
    private String updated_at;

    public Tallo(Arbol arbol, String medida) {

        this.arbol = arbol;
        this.arbol_id = arbol.getId();
        this.medida = medida;
        this.created_at = sdf.format(new Date());
        this.updated_at = sdf.format(new Date());
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public Tallo() {
    }
}
