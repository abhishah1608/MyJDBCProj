package com.demo.dboperation;

/**
 * 
 * 
 * 
 *
 **/

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SQLParameter {
    private final Object value;
    private final DataType type; 
    private final int index;

    /**
     * 
     * **/
    public SQLParameter(Object value, DataType type, int index) {
        this.value = value;
        this.type = type;
        this.index = index;
    }

    public void setParameter(PreparedStatement ps) throws SQLException {
        switch (type) {
            case String:
                ps.setString(this.index, (String) this.value);
                break;
            case Integer:
                ps.setInt(this.index, (Integer) this.value);
                break;
            case Double:
                ps.setDouble(this.index, (Double) this.value);
                break;
            case Boolean:
                ps.setBoolean(this.index, (Boolean) this.value);
                break;
            case Long:
                ps.setLong(this.index, (Long) this.value);
                break;
            case Float:
                ps.setFloat(this.index, (Float) this.value);
                break;
            case Date:
                ps.setDate(this.index, (java.sql.Date) this.value);
                break;
            case LocalDateT:
            	ps.setTimestamp(this.index, Timestamp.valueOf((LocalDateTime)this.value));
            	break;
            default:
                throw new SQLException("Unsupported SQL Parameter Type: " + type);
        }
    }
}



