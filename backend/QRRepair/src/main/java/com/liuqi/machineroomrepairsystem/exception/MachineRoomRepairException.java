package com.liuqi.machineroomrepairsystem.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class MachineRoomRepairException extends RuntimeException{
    private HttpStatusCode code = HttpStatus.INTERNAL_SERVER_ERROR; // default value is 500
    public MachineRoomRepairException(){}
    public MachineRoomRepairException(String message){
        super(message);
    }
    public MachineRoomRepairException(HttpStatusCode code,String message){
        super(message);
        this.code = code;
    }
    public MachineRoomRepairException(String message,Throwable cause){
        super(message,cause);
    }
}
