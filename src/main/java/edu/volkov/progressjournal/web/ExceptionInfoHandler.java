package edu.volkov.progressjournal.web;

import edu.volkov.progressjournal.util.ValidationUtil;
import edu.volkov.progressjournal.util.exception.ErrorInfo;
import edu.volkov.progressjournal.util.exception.ErrorType;
import edu.volkov.progressjournal.util.exception.IllegalRequestDataException;
import edu.volkov.progressjournal.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static edu.volkov.progressjournal.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionInfoHandler {

    private static final Map<String, String> EXCEPTION_MESS_MAP = new HashMap<>();

    static {
        EXCEPTION_MESS_MAP.put("error.appError", "Application error");
        EXCEPTION_MESS_MAP.put("error.dataNotFound", "Data not found");
        EXCEPTION_MESS_MAP.put("error.validationError", "Validation error");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(HttpServletRequest req, NotFoundException e) {
        log.info("handleNotFoundException()");
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> handleIllegalRequestDataError(HttpServletRequest req, Exception e) {
        log.info("handleIllegalRequestDataError()");
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, Exception e) {
        log.info("handleError()");
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logStackTrace, ErrorType errorType, String... details) {
        log.info("logAndGetErrorInfo()");
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logStackTrace, errorType);

        ErrorInfo errInfo = new ErrorInfo(req.getRequestURL(), errorType,
                EXCEPTION_MESS_MAP.get(errorType.getErrorCode()),
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});

        return ResponseEntity.status(errorType.getStatus()).body(errInfo);
    }
}