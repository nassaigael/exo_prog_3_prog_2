package com.nassaigael.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class GradeChange {
    private final Instant time;
    private final double value;
    private final String reason;
}
