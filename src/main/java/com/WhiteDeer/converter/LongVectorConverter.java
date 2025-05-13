package com.WhiteDeer.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Converter(autoApply = true)
public class LongVectorConverter implements AttributeConverter<Vector<Long>, List<Integer>> {

    @Override
    public List<Integer> convertToDatabaseColumn(Vector<Long> attribute) {
        if (attribute == null) {
            return null;
        }
        List<Integer> intList = new ArrayList<>();
        for (Long l : attribute) {
            intList.add(l.intValue());
        }
        return intList;
    }

    @Override
    public Vector<Long> convertToEntityAttribute(List<Integer> dbData) {
        if (dbData == null) {
            return null;
        }
        Vector<Long> longVector = new Vector<>();
        for (Integer i : dbData) {
            longVector.add(Long.valueOf(i));
        }
        return longVector;
    }
}