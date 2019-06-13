package com.grupo2.iomt.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.grupo2.iomt.dao.TokenDao;
import com.grupo2.iomt.entity.Token;

/*
author: Allan Orellana
version: 1
description: this database has handle all DAOs
 */

@Database(entities = {Token.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract TokenDao getTokenDAO();
}
