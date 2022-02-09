package model;

import java.sql.Timestamp;

public class BeginTime {
    private String code;
    private Timestamp begin_time;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Timestamp getBegin_time() {
        return begin_time;
    }
    public void setBegin_time(Timestamp begin_time) {
        this.begin_time = begin_time;
    }

}
