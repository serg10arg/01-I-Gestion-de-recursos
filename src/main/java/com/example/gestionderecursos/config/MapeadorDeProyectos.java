package com.example.gestionderecursos.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapeadorDeProyectos {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        /**
         * Le ordena a ModelMapper que solo mapee propiedades si el nombre y el tipo de dato de la propiedad de origen
         * y destino son exactamente iguales
         */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }



}
