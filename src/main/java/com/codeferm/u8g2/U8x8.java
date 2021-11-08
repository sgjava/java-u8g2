/*
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */
package com.codeferm.u8g2;

import static org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT;
import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniField;
import org.fusesource.hawtjni.runtime.JniMethod;
import org.fusesource.hawtjni.runtime.Library;
import static org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER;

/**
 * Java U8x8 constants.
 *
 * @author Steven P. Goldsmith
 * @version 1.0.0
 * @since 1.0.0
 */
@JniClass
public class U8x8 {

    /**
     * java-periphery library.
     */
    private static final Library LIBRARY = new Library("java-u8g2", U8x8.class);

    /**
     * Load library.
     */
    static {
        LIBRARY.load();
        init();
    }

    /**
     * Load constants.
     */
    @JniMethod(flags = {CONSTANT_INITIALIZER})
    private static native void init();
    /**
     * Constants generated by CodeGen.java.
     */
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D0;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_SPI_CLOCK;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D1;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_SPI_DATA;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D2;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D3;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D4;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D5;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D6;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_D7;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_E;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_CS;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_DC;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_RESET;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_I2C_CLOCK;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_I2C_DATA;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_CS1;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_CS2;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_OUTPUT_CNT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_SELECT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_NEXT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_PREV;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_HOME;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_UP;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_MENU_DOWN;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_INPUT_CNT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_CNT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_PIN_NONE;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_SETUP_MEMORY;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_INIT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_SET_POWER_SAVE;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_SET_FLIP_MODE;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_SET_CONTRAST;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_DRAW_TILE;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DISPLAY_REFRESH;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_INIT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_SEND_CMD;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_SEND_ARG;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_SEND_DATA;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_START_TRANSFER;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_CAD_END_TRANSFER;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_BYTE_SET_DC;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_GPIO_AND_DELAY_INIT;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DELAY_MILLI;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DELAY_10MICRO;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DELAY_100NANO;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DELAY_NANO;
    @JniField(flags = {CONSTANT})
    public static int U8X8_MSG_DELAY_I2C;
}
