package com.algawork.algalog.api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algawork.algalog.domain.model.Cliente;
import com.algawork.algalog.domain.repository.ClienteResository;
import com.algawork.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	/*
	@PersistenceContext
	private EntityManager manager;
	manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	*/
	
	
	private ClienteResository clienteResository;
	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteResository.findAll();
		//return clienteResository.findByNome("Tiago Ventura");
		//return clienteResository.findByNomeContaining("a");
		
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		return clienteResository.findById(clienteId)
				.map(cliente -> ResponseEntity.ok(cliente))
				.orElse(ResponseEntity.notFound().build());	
		/*
		 * 
		 * 
		 * Optional<Cliente> cliente = clienteResository.findById(clienteId);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
		*/
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		//return clienteResository.save(cliente);
		
		return catalogoClienteService.salvar(cliente);
		
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente){
				
		if(!clienteResository.existsById(clienteId))
			return ResponseEntity.notFound().build();
		
		cliente.setId(clienteId);
		cliente = catalogoClienteService.salvar(cliente); // clienteResository.save(cliente);
		return ResponseEntity.ok(cliente);	
	}
	 
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteResository.existsById(clienteId))
			return ResponseEntity.notFound().build();
		
		//clienteResository.deleteById(clienteId);
		catalogoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
		
			
	}

}










