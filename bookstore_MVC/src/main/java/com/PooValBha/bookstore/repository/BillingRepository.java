package com.PooValBha.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.PooValBha.bookstore.model.Customer;

public interface BillingRepository extends CrudRepository<Customer, Long> {

}
