package com.a304.wildworker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ChangeUserInfoRequest {

    String name;
    Integer titleType;
    Long mainTitleId;
    Integer characterType;
}
