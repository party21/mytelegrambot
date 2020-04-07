package com.mytelegrambot.mybot;

import com.mytelegrambot.mybot.entity.Employee;
import com.mytelegrambot.mybot.repository.StatusRepository;
import com.mytelegrambot.mybot.repository.EmployeeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class KnownChatIds {

    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;

    void addChatId(Long id, Employee user) {
        log.info("Added chat id {}, number {}.", id, user.getPhoneNumber());
        employeeRepository.save(new Employee());
    }

    public List<Employee> getChatIds() {
        return employeeRepository.findAll();
    }

    public String checkPhone(String phone) {
        String number ="works";
        employeeRepository.findPhoneNumber("<phone_number>").getPhoneNumber();
        return number;
    }

    Optional<Employee> getChatIdContact(int id) {
        return employeeRepository.findById(id);
    }
}
