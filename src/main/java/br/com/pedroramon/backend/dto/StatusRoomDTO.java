package br.com.pedroramon.backend.dto;

import br.com.pedroramon.backend.dto.interfaces.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.Default;

public record StatusRoomDTO(
    @Null(groups = Default.class)
    @NotNull(groups = OnUpdate.class)
    @PositiveOrZero(groups = OnUpdate.class)
    Long id, 
    @NotBlank(groups = {Default.class, OnUpdate.class})
    String description
) {}
