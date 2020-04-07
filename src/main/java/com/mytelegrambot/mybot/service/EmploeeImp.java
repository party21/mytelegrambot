package com.mytelegrambot.mybot.service;

import com.mytelegrambot.mybot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmploeeImp implements Employee {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<com.mytelegrambot.mybot.entity.Employee> findAll() {
        return employeeRepository.findAll();
    }
}
