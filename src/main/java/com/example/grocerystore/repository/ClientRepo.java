package com.example.grocerystore.repository;

import com.example.grocerystore.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<ClientEntity, Long>{
}
