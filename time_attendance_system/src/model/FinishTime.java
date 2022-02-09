package model;

import java.sql.Timestamp;

public class FinishTime {
    private String code;
    private Timestamp finish_time;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Timestamp getFinish_time() {
        return finish_time;
    }
    public void setFinish_time(Timestamp finish_time) {
        this.finish_time = finish_time;
    }

}
