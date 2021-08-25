package com.algawork.algalog.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algawork.algalog.domain.model.Cliente;

@Repository
public interface ClienteResository extends JpaRepository<Cliente, Long> {
	
	List<Cliente> findByNome(String nome);
	List<Cliente> findByNomeContaining(String nome);
	List<Cliente> findByEmail(String email);
	
}
