package com.a304.wildworker.dto.response.common;


import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.dto.response.MessageDto;
import com.a304.wildworker.exception.base.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSBaseResponse<T> {

    protected final Type type;
    protected final SubType subType;
    protected final T data;

    public static WSBaseResponse<?> exception(HttpStatus status, String message) {
        return new WSBaseResponse<>(Type.EXCEPTION, ExceptionType.valueOf(status.name()),
                MessageDto.of(message));
    }

    public static WSBaseResponse<?> exception(CustomException e) {
        return WSBaseResponse.exception(e.getStatus(), e.getMessage());
    }

    public static BodyBuilder station(StationType subType) {
        return new Builder(Type.STATION, subType);
    }

    public static BodyBuilder mining(MiningType subType) {
        return new Builder(Type.MINING, subType);
    }

    public static BodyBuilder coin(TransactionType subType) {
        return new Builder(Type.COIN, subType);
    }

    public static BodyBuilder title(TitleType subType) {
        return new Builder(Type.TITLE, subType);
    }

    public interface BodyBuilder {

        <T> WSBaseResponse<T> data(T data);

        <T> WSBaseResponse<T> build();
    }

    private static class Builder implements BodyBuilder {

        private final Type type;
        private final SubType subType;

        private Builder(Type type, SubType subType) {
            this.type = type;
            this.subType = subType;
        }

        @Override
        public <T> WSBaseResponse<T> data(T data) {
            return new WSBaseResponse<>(this.type, this.subType, data);
        }

        @Override
        public <T> WSBaseResponse<T> build() {
            return new WSBaseResponse<>(this.type, this.subType, null);
        }

    }
}
