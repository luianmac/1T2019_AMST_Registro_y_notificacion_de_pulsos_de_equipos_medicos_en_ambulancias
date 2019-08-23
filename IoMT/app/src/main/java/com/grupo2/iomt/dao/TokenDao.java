package com.grupo2.iomt.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo2.iomt.entity.Token;

import java.util.List;

/**
 * The interface Token dao.
 * @author Allan Orelllana
 * @version 1.o
 */

@Dao
public interface TokenDao {
    /**
     * Insert.
     *
     * @param token the token
     */
    @Insert
    public void insert (Token... token);

    /**
     * Update.
     *
     * @param items the items
     */
    @Update
    public void update(Token... items);

    /**
     * Delete.
     *
     * @param item the item
     */
    @Delete
    public void delete(Token item);

    /**
     * Gets items.
     *
     * @return the items
     */
//Custom queries
    @Query("SELECT * FROM tokens")
    public List<Token> getItems();

    /**
     * Gets token by token.
     *
     * @param token_string the token string
     * @return the token by token
     */
    @Query("Select * from tokens where token_string = :token_string ")
    public Token getTokenByToken(String token_string);

    /**
     * Gets token by id.
     *
     * @param id the id
     * @return the token by id
     */
    @Query("Select * from tokens where id = :id")
    public Token getTokenById(Long id);

}
