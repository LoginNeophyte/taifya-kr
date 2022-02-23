package edu.psuti.alexandrov.interpret;

public class RealContainer extends Container<Double> {

    @Override
    Double parseValue(String newValue) {
        return Double.parseDouble(newValue);
    }

    @Override
    Double executeComputing(String opDef, Double operand) {
        return switch (opDef) {
            case "plus" -> value + operand;
            case "minus" -> value - operand;
            case "*" -> value * operand;
            case "/" -> value / operand;
            default -> throw new IllegalArgumentException("Недопустимая операция вычисления '"
                                                            + opDef + "' для типа real");
        };
    }

    @Override
    boolean executeComparing(String opDef, Double operand) {
        return switch (opDef) {
            case "==" -> value.doubleValue() == operand.doubleValue();
            case "!=" -> value.doubleValue() != operand.doubleValue();
            case ">=" -> value >= operand;
            case "<=" -> value <= operand;
            case ">" -> value > operand;
            case "<" -> value < operand;
            default -> false;
        };
    }

    @Override
    String nonNullRepresentaion() {
        return (value < 0 ? "1_" : "0_") + Integer.toBinaryString(value.intValue());
    }
}
