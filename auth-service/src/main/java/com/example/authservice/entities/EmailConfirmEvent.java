package com.example.authservice.entities;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class EmailConfirmEvent {

    private String uuid;

    private String email;
}
