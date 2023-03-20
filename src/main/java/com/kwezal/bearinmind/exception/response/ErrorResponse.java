package com.kwezal.bearinmind.exception.response;

import java.util.Set;
import org.springframework.lang.Nullable;

public record ErrorResponse(String code, @Nullable Set<String> arguments, String origin) {}
