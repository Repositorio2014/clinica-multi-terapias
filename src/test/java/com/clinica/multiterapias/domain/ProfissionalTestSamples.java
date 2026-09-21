package com.clinica.multiterapias.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProfissionalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Profissional getProfissionalSample1() {
        return new Profissional()
            .id(1L)
            .nome("nome1")
            .cpf("cpf1")
            .registroConselho("registroConselho1")
            .telefone("telefone1")
            .email("email1");
    }

    public static Profissional getProfissionalSample2() {
        return new Profissional()
            .id(2L)
            .nome("nome2")
            .cpf("cpf2")
            .registroConselho("registroConselho2")
            .telefone("telefone2")
            .email("email2");
    }

    public static Profissional getProfissionalRandomSampleGenerator() {
        return new Profissional()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .registroConselho(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
