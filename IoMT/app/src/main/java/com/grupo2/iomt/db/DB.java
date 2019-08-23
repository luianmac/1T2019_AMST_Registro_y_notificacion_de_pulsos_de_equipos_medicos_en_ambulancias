package com.grupo2.iomt.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.grupo2.iomt.dao.TokenDao;
import com.grupo2.iomt.entity.Token;



/**
 * The type Db.
 *
 * @author Allan Orellana
 * @version 1
 *
 */
@Database(entities = {Token.class}, version = 1)
public abstract class DB extends RoomDatabase {
    /**
     * Gets token dao.
     *
     * @return the token dao
     */
    public abstract TokenDao getTokenDAO();
}
