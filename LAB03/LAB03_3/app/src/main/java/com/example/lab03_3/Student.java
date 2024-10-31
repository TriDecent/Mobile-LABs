package com.example.lab03_3;

import java.math.BigInteger;
import java.time.LocalDate;

public record Student(BigInteger Id, String Name, String Email, LocalDate DateOnly, Status Status) {

}
