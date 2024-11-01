package com.example.lab03_3.models;

import java.math.BigInteger;
import java.time.LocalDate;

public record Student(BigInteger Id, String Name, String Email, LocalDate DateOnly,
                      com.example.lab03_3.enums.Status Status) {

}
