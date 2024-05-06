package com.mchen2.myapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RestResponse<T> implements Serializable {
    private static final long serialVersionUID = -1080304015437611873L;
    private int status;
    private T data;
    private String message;
    private final Date timestamp = new Date();

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> resp = new RestResponse();
        resp.status = 200;
        resp.data = data;
        return resp;
    }

    public static <T> RestResponse<T> error(Throwable e) {
        return error(e.getLocalizedMessage());
    }

    public static <T> RestResponse<T> error(String errorMessage) {
        RestResponse<T> resp = new RestResponse();
        resp.status = 500;
        resp.message = errorMessage;
        return resp;
    }

    public RestResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public RestResponse(T data) {
        this.status = 200;
        this.data = data;
    }

    public int getStatus() {
        return this.status;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RestResponse)) {
            return false;
        } else {
            RestResponse<?> other = (RestResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatus() != other.getStatus()) {
                return false;
            } else {
                label49: {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data == null) {
                            break label49;
                        }
                    } else if (this$data.equals(other$data)) {
                        break label49;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$timestamp = this.getTimestamp();
                Object other$timestamp = other.getTimestamp();
                if (this$timestamp == null) {
                    if (other$timestamp != null) {
                        return false;
                    }
                } else if (!this$timestamp.equals(other$timestamp)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RestResponse;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getStatus();
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : $timestamp.hashCode());
        return result;
    }

    public String toString() {
        return "RestResponse(status=" + this.getStatus() + ", data=" + this.getData() + ", message=" + this.getMessage() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public RestResponse() {
    }
}
