package com.algawork.algalog.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algawork.algalog.domain.model.Cliente;
import com.algawork.algalog.domain.model.Entrega;
import com.algawork.algalog.domain.model.StatusEntrega;
import com.algawork.algalog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {
	
	EntregaRepository entregaRepository;
	CatalogoClienteService catalogoClienteService;
	
	@Transactional
	public Entrega solicitar(Entrega entrega) {
		Cliente cliente =  catalogoClienteService.buscar(entrega.getCliente().getId());
				
		entrega.setCliente(cliente);
				
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		
		return entregaRepository.save(entrega);
	}

}
