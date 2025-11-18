package com.nassaigael.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GradeChange {
    private final Instant time;
    private final double value;
    private final String reason;
}
