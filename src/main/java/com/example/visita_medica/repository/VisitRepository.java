package com.example.visita_medica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.visita_medica.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit,Long> {

}
