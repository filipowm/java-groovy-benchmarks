package com.filipowm.benchmarks;

public interface CharacterBenchmark {

    void isSpace(long repeatCount);

    void digit(long repeatCount);

    void isDigit(long repeatCount);

    void isLetter(long reps);

    void isLetterOrDigit(long reps);

    void isLowerCase(long reps);

    void isSpaceChar(long reps);

    void isUpperCase(long reps);

    void isWhitespace(long reps);

    void toLowerCase(long reps);

    void toUpperCase(long reps);
}
