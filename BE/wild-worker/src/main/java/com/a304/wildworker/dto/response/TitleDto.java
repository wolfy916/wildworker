package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TitleDto {

    private long id;
    private String name;

    public static TitleDto of(Title title) {
        return new TitleDto(title.getId(), title.getName());
    }
}
