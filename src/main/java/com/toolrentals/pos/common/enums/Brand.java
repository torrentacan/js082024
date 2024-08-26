package com.toolrentals.pos.common.enums;

/**
 * enum for specific tool manufacturers.
 */
public enum Brand {
    STIHL(0, "Stihl"),
    WERNER(1, "Werner"),
    DEWALT(2, "DeWalt"),
    RIDGID(3, "Ridgid"),
    UNDEFINED(-999999, "Undefined Brand");

    private int value;
    private String name;

    Brand(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Brand getBrand(int value) {
        Brand result = Brand.UNDEFINED;
        for (Brand brand : Brand.values()) {
            if (brand.getValue() == value) {
                result = brand;
            }
        }
        return result;
    }

    public static Brand getBrand(String name) {
        Brand result = UNDEFINED;
        for (Brand brand : Brand.values()) {
            if (brand.getName().equals(name)) {
                result = brand;
            }
        }
        return result;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
