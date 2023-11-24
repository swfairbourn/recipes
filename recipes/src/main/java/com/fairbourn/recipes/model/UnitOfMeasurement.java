package com.fairbourn.recipes.model;

public enum UnitOfMeasurement {
    BOTTLE("Bottle"),
    CAN("Can"),
    CENTIMETER("Centimeter"),
    CUP("Cup"),
    DASH("Dash"),
    DOZEN("Dozen"),
    DROP("Drop"),
    EACH("Each"),
    FLUID_OUNCE("Fluid Ounce"),
    GALLON("Gallon"),
    GRAM("Gram"),
    INCH("Inch"),
    JAR("Jar"),
    KILOGRAM("Kilogram"),
    LITER("Liter"),
    MILLILITER("Milliliter"),
    OUNCE("Ounce"),
    PACKAGE("Package"),
    PINT("Pint"),
    PINCH("Pinch"),
    POUND("Pound"),
    QUART("Quart"),
    SQUARE_CENTIMETER("Square Centimeter"),
    SQUARE_INCH("Square Inch"),
    SMIDGEN("Smidgen"),
    TABLESPOON("Tablespoon"),
    TEASPOON("Teaspoon"),
    UNIT("Unit");

    private final String label;

    UnitOfMeasurement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}

