package com.grupo2.iomt.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/*
author: Allan Orellana
version: 01
description: This table storage tokens
 */
@Entity(tableName = "tokens")
public class Token {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "token_string")
    private String token_string;

    //---functions
    @Override
    public String toString(){
        return this.token_string;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getToken_string() {
        return token_string;
    }

    public void setToken_string(String token_string) {
        this.token_string = token_string;
    }
}
