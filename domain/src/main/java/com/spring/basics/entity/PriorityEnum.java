package com.spring.basics.entity;

public enum PriorityEnum {
    URGENTE("Urgente"),
    UMA_SEMANA("Em uma semana"),
    DUAS_SEMANAS("Em duas semanas"),
    UM_MES("Em um mês"),
    DATA_ESPECIFICA("Na data especificada");

    PriorityEnum(String description) {
    }
}
