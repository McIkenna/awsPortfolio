package com.ikenna.portfolios.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleInfoIdException(InfoException ex, WebRequest request){
        InfoIdExceptionResponse exceptionResponse = new InfoIdExceptionResponse(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler
    public final ResponseEntity<Object> handleWorkException(WorkException ex, WebRequest request){
        WorkExceptionResponse exceptionResponse = new WorkExceptionResponse(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEducationException(EducationException e, WebRequest request){
        EducationExceptionResponse exceptionResponse = new EducationExceptionResponse(e.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleSkillException(SkillException e, WebRequest request){
        SkillExceptionResponse exceptionResponse = new SkillExceptionResponse(e.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleWorkException(ProjectException ex, WebRequest request){
        ProjectExceptionResponse exceptionResponse = new ProjectExceptionResponse(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAddressException(AddressException ex, WebRequest request){
        AddressExceptionResponse exceptionResponse = new AddressExceptionResponse(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
