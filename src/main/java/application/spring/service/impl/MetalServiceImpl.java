package application.spring.service.impl;

import application.spring.entity.Metal;
import application.spring.repository.MetalRepository;
import application.spring.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public class MetalServiceImpl implements MetalService {


    @Override
    public Metal findMetalByNameOrDate(String name, LocalDate localDate) {
        return null;
    }

    @Override
    public MetalRepository getRepository() {
        return null;
    }
}
