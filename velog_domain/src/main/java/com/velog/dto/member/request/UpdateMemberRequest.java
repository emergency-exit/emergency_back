package com.velog.dto.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {

    @NotBlank
    private String name;

    private String velogName;

    private String description;

    public UpdateMemberRequest(String name, String velogName, String description) {
        this.name = name;
        this.velogName = velogName;
        this.description = description;
    }

    public static UpdateMemberRequest testInstance(String name, String velogName, String description) {
        return new UpdateMemberRequest(name, velogName, description);
    }

}
