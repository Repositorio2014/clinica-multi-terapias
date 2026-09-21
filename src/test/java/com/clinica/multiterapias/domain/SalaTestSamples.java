package com.clinica.multiterapias.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SalaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Sala getSalaSample1() {
        return new Sala().id(1L).nome("nome1").descricao("descricao1").capacidade(1);
    }

    public static Sala getSalaSample2() {
        return new Sala().id(2L).nome("nome2").descricao("descricao2").capacidade(2);
    }

    public static Sala getSalaRandomSampleGenerator() {
        return new Sala()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .capacidade(intCount.incrementAndGet());
    }
}
