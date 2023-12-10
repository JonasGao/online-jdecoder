package com.jonas.ojd;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record CompileRequest(@NotEmpty @Size(max = 3) Collection<@NotNull @Valid JavaSelect> javaSelects,
                             @NotBlank String mainClass,
                             @NotBlank String javaCode,
                             Boolean verbose
) {

    public record JavaSelect(@NotBlank String javaInstallSelect,
                             @NotNull Integer sourceVersion) {
    }
}
