package com.kwezal.bearinmind.exception.response;

import jakarta.annotation.Nullable;
import java.util.Set;

public record ErrorResponse(String code, @Nullable Set<String> arguments, String origin) {}
