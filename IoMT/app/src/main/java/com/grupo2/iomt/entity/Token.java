package com.grupo2.iomt.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This table storages tokens
 * @author Allan Orellana
 * @version 1.0
 */

@Entity(tableName = "tokens")
public class Token {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "token_string")
    private String token_string;

    /**
     * Instantiates a new Token.
     *
     * @param token_string the token string
     */
    public Token(String token_string) {
        this.token_string = token_string;
    }

    //---functions
    @Override
    public String toString(){
        return this.token_string;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @NonNull
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

    /**
     * Gets token string.
     *
     * @return the token string
     */
    public String getToken_string() {
        return token_string;
    }

    /**
     * Sets token string.
     *
     * @param token_string the token string
     */
    public void setToken_string(String token_string) {
        this.token_string = token_string;
    }
}
