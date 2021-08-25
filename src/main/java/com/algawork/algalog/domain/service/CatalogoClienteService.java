package com.algawork.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algawork.algalog.domain.exception.NegocioException;
import com.algawork.algalog.domain.model.Cliente;
import com.algawork.algalog.domain.repository.ClienteResository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {
	
	private ClienteResository clienteResository;
	
	public Cliente buscar(Long clienteId) {
		return clienteResository.findById(clienteId)
		.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {	
		boolean emailEmUso = clienteResository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com esse email");
		}
		
		return clienteResository.save(cliente);
	}
	
	
	@Transactional
	public void excluir(Long clienteId) {
		clienteResository.deleteById(clienteId);
	}

}
