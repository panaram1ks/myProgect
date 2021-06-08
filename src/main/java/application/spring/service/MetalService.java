package application.spring.service;

import application.spring.entity.Metal;
import application.spring.repository.MetalRepository;


import java.time.LocalDate;

public interface MetalService extends Service<Integer, Metal, MetalRepository> {

    Metal findMetalByNameOrDate(String name, LocalDate localDate);

}
