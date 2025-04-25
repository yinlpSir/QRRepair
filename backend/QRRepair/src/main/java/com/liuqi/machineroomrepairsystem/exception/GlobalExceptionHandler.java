package com.liuqi.machineroomrepairsystem.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;

    /**
     *  IllegalArgumentException 非法参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e){
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<Object> handleSizeLimitExceededException(SizeLimitExceededException e){
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,"您的请求过大,请保持在15MB以内!");
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceededException(FileSizeLimitExceededException e){
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,"单个文件在2M以内!");
    }

    /**
     * 重写 ResponseEntityExceptionHandler 中的 MethodArgumentNotValidException 异常的处理方法
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//        return this.buildErrorResponse(ex.getStatusCode(),messageSource.getMessage(ex.getBindingResult().getAllErrors().get(0),null));
        List<String> errorMsgs = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        errorMsgs.forEach(System.out::println);

        return this.buildErrorResponse(ex.getStatusCode(),"您的请求参数有误!");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e){
        List<String> errorMsgs = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        errorMsgs.forEach(System.out::println);
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,"您的请求参数有误!");
    }

    /**
     * 项目自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MachineRoomRepairException.class)
    public ResponseEntity<Object> handleMachineRoomRepairException(MachineRoomRepairException e){
        return this.buildErrorResponse(e.getCode(),e.getMessage());
    }

    /**
     *  SQL完整性约束违反异常
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
//        e.printStackTrace();
//        System.out.println(e.getMessage()); //类似于:Duplicate entry '1111' for key 'lab.lab_lab_name_uindex'
//        log.info("错误信息为:{}", ex.getMessage());
        String msg;
        if(e.getMessage().contains("Duplicate entry")) {
            String[] split = e.getMessage().split(" ");//空格分隔
            msg = split[2] + " 已存在,请更改!";
        }else msg = "已存在的值,请更改您的值!";

        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,msg);
    }

    /**
     *  所有 sql 异常 都继承自 SQLException
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSqlException(SQLException e){
        return this.buildErrorResponse(HttpStatus.resolve(e.getErrorCode()),"数据库操作异常,请联系管理员!");
    }

    /**
     *  Build error response
     * @param httpStatusCode
     * @param message
     * @return
     */
    protected ResponseEntity<Object> buildErrorResponse(HttpStatusCode httpStatusCode, String message){
        var result = new HashMap<String,String>();
        result.put("msg",message);
        return ResponseEntity.status(httpStatusCode).body(result);
    }

}
