package com.grupo2.iomt.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo2.iomt.entity.Token;

import java.util.List;
/*
autor: Allan Orellana
version: 01
description: This is the Dao for entity Token
 */
@Dao
public interface TokenDao {
    @Insert
    public void insert (Token... token);
    @Update
    public void update(Token... items);
    @Delete
    public void delete(Token item);

    //Custom queries
    @Query("SELECT * FROM tokens")
    public List<Token> getItems();

    @Query("Select * from tokens where token_string = :token_string ")
    public Token getTokenByToken(String token_string);

    @Query("Select * from tokens where id = :id")
    public Token getTokenById(Long id);

}
