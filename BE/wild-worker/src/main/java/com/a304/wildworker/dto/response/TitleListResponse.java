package com.a304.wildworker.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class TitleListResponse {

    int titleType;
    Long mainTitleId;
    List<TitleDto> titles;
}
