package com.example.demo
import org.springframework.data.jpa.repository.JpaRepository

interface RetwitRepository : JpaRepository<Mensaje, Int>