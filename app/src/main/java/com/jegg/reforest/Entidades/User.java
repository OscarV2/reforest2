package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 5/05/17.
 */
@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "email")
    private String email;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "persona_id")
    private int persona_id;

    public User() {
    }

    public User(String name, String email, String password, int persona_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.persona_id = persona_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdPersona() {
        return persona_id;
    }

    public void setIdPersona(int persona_id) {
        this.persona_id = persona_id;
    }

}
