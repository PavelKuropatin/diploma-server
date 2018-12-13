package by.bntu.diploma.diagram.web.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class ResponseError {

    @JsonProperty("error")
    private String message;

    public ResponseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseError that = (ResponseError) o;
        return Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.message);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "message='" + this.message + '\'' +
                '}';
    }
}
