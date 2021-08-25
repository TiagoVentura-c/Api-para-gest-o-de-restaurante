package com.algawork.algalog.domain.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ClienteIdInput {
	@NotNull
	private Long id;
}
