package com.asela.java.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import com.asela.Utility;

public class Base64Test {

    private static final Map<Integer, Character> RFC4648_ALPHABET = new HashMap<>(64, 1);
    static {
        AtomicInteger index = new AtomicInteger(0);
        HashMap<Character, Character> ranges = new LinkedHashMap<>(3, 1);
        ranges.put('A', 'Z');
        ranges.put('a', 'z');
        ranges.put('0', '9');
        ranges.forEach(
                (F, T) 
                ->  RFC4648_ALPHABET.putAll(
                        IntStream.rangeClosed(F, T)
                        .boxed()
                        .collect(
                                Collectors.toMap(
                                        k -> index.getAndIncrement(), 
                                        v -> new Character((char) v.intValue())
                                 )
                        )
                   )
         );

        RFC4648_ALPHABET.put(index.getAndIncrement(), '-');
        RFC4648_ALPHABET.put(index.getAndIncrement(), '_');

        Utility.decoratedPrint(RFC4648_ALPHABET, "RFC4648 Alphabet");

    }
    private static final Character RFC4648_PAD = new Character('=');

    @Test
    public void encodingAndDecoding() throws Exception {

        String subject = "ManManMan";

        String enc = Base64.getEncoder().encodeToString("ManManMan".getBytes());
        System.out.println(enc);
        System.out.println(Arrays.toString(enc.getBytes()));

        String dec = new String(Base64.getDecoder().decode(enc));
        System.out.println(dec);
        System.out.println(Arrays.toString(dec.getBytes()));

        assertThat(subject, is(dec));
    }

    @Test
    public void manualBase64EncodingNonPaddingCase() throws Exception {

        String subject = "ManManMan";
        Utility.decoratedPrint(subject, "Subject");

        byte[] bytes = subject.getBytes();
        String subjectInBinary = 
                IntStream.range(0, bytes.length)
                    .map(i -> bytes[i]).boxed()
                    .map(Integer::toBinaryString)
                    .map(s -> "0" + s)
                    .peek(System.out::println).reduce((s1, s2) -> s1 + s2)
                    .get();

        Utility.decoratedPrint(subjectInBinary, "Subject As Binary String : ");

        String[] split = subjectInBinary.split("(?<=\\G.{6})");

        Utility.decoratedPrint(Arrays.toString(split), "Split in 6");

        String base64String = 
                    Arrays.stream(split)
                        .map(s -> Integer.parseInt(s, 2))
                        .map(RFC4648_ALPHABET::get)
                        .map(String::valueOf)
                        .reduce((s1, s2) -> s1 + s2)
                        .get();

        Utility.decoratedPrint(base64String, "Finaly Encoded String");

        String enc = Base64.getEncoder().encodeToString("ManManMan".getBytes());

        assertThat(base64String, is(enc));

    }
}
