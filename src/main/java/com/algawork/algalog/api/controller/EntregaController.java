package com.algawork.algalog.api.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algawork.algalog.api.assembler.EntregaAssemler;
import com.algawork.algalog.api.model.DestinatarioModel;
import com.algawork.algalog.api.model.EntregaModel;
import com.algawork.algalog.domain.model.Entrega;
import com.algawork.algalog.domain.model.input.EntregaInput;
import com.algawork.algalog.domain.repository.EntregaRepository;
import com.algawork.algalog.domain.service.FinalizacaoEntregaService;
import com.algawork.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaRepository entregaRepository;
	private EntregaAssemler entregaAssemler;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@Validated @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssemler.toEntity(entregaInput);
		
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
		return entregaAssemler.toModel(entregaSolicitada);
	}
	
	@GetMapping
	public List<EntregaModel> listar(){
		return entregaAssemler.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaAssemler.toModel(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}

}












