package com.grubstay.server.helper;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PGIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {

        final String PREFIX_VALUE="PG";
        Connection connection = session.connection();

        try{
            Statement stmt=connection.createStatement();
            //String sqlStmt="select count(pg_id) from paying_guest";
            String sqlStmt = "select MAX(CAST(SUBSTR(TRIM(pg_id),3) AS UNSIGNED)) as last_pg_id from paying_guest";
            ResultSet resultSet = stmt.executeQuery(sqlStmt);
            if(resultSet.next()){
                int id=resultSet.getInt(1)+1;
                String pgID=PREFIX_VALUE+new Integer(id).toString();
                return pgID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
