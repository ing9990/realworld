package com.realworldbackend.common.exception.dto;

import com.realworldbackend.common.exception.ErrorCode;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private String code;
    private List<FieldError> errors;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    static class FieldError {
        private String field;
        private String value;
        private String reason;

        public static List<FieldError> of(final String field, final String value, final String reason) {
            return List.of(new FieldError(field, value, reason));
        }

        private static List<FieldError> of(final BindingResult result) {
            return result.getAllErrors().stream().map(error -> {
                var field = (org.springframework.validation.FieldError) error;

                return FieldError.builder()
                        .field(field.getField())
                        .reason(field.getDefaultMessage())
                        .value(field.getRejectedValue() == null ? "" : field.getRejectedValue().toString())
                        .build();
            }).collect(Collectors.toList());
        }
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();

        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());

        return new ErrorResponse(ErrorCode.INVALID_INPUT, errors);
    }

}
