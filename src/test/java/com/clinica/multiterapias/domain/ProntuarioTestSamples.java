package com.clinica.multiterapias.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProntuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prontuario getProntuarioSample1() {
        return new Prontuario().id(1L).titulo("titulo1");
    }

    public static Prontuario getProntuarioSample2() {
        return new Prontuario().id(2L).titulo("titulo2");
    }

    public static Prontuario getProntuarioRandomSampleGenerator() {
        return new Prontuario().id(longCount.incrementAndGet()).titulo(UUID.randomUUID().toString());
    }
}
