/*
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */
package com.codeferm.u8g2.demo;

import com.codeferm.u8g2.U8g2;
import static com.codeferm.u8g2.U8g2.U8G2_R0;
import static com.codeferm.u8g2.U8g2.u8x8_arm_linux_gpio_and_delay;
import static com.codeferm.u8g2.U8g2.u8x8_byte_arm_linux_hw_i2c;
import static com.codeferm.u8g2.U8g2.u8x8_byte_arm_linux_hw_spi;
import static com.codeferm.u8g2.U8x8.U8X8_PIN_DC;
import static com.codeferm.u8g2.U8x8.U8X8_PIN_RESET;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;

/**
 * Dynamically allocate u8g2_t structure and call display setup. Change the setupXX call to match your display.
 *
 * @author Steven P. Goldsmith
 * @version 1.0.0
 * @since 1.0.0
 */
public class Display {

    /**
     * Logger.
     */
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Display.class);

    /**
     * Initialize I2C display and return pointer to u8g2_t structure.
     *
     * @param gpio GPIO chip number.
     * @param bus I2C bus number.
     * @param address I2C address.
     * @return Pointer to u8g2_t structure.
     */
    public long init(final int gpio, final int bus, final int address) {
        final var u8g2 = U8g2.initU8g2();
        U8g2.setupSsd1306I2c128x64NonameF(u8g2, U8G2_R0, u8x8_byte_arm_linux_hw_i2c, u8x8_arm_linux_gpio_and_delay);
        U8g2.setGpioChip(u8g2, gpio);
        U8g2.setI2cBus(u8g2, bus);
        U8g2.setI2CAddress(u8g2, address * 2);
        U8g2.initDisplay(u8g2);
        logger.debug(String.format("Size %d x %d, draw color %d", U8g2.getDisplayWidth(u8g2), U8g2.getDisplayHeight(u8g2), U8g2.
                getDrawColor(u8g2)));
        return u8g2;
    }

    /**
     * Initialize SPI display and return pointer to u8g2_t structure.
     *
     * @param gpio GPIO chip number.
     * @param bus SPI bus number.
     * @param dc DC pin.
     * @param reset RESET pin.
     * @return Pointer to u8g2_t structure.
     */
    public long init(final int gpio, final int bus, final int dc, final int reset) {
        final var u8g2 = U8g2.initU8g2();
        U8g2.setupSsd1306128x64NonameF(u8g2, U8G2_R0, u8x8_byte_arm_linux_hw_spi, u8x8_arm_linux_gpio_and_delay);
        U8g2.setGpioChip(u8g2, gpio);
        U8g2.setSpiBus(u8g2, bus);
        U8g2.setPin(u8g2, U8X8_PIN_DC, dc);
        U8g2.setPin(u8g2, U8X8_PIN_RESET, reset);
        U8g2.initDisplay(u8g2);
        logger.debug(String.format("Size %d x %d, draw color %d", U8g2.getDisplayWidth(u8g2), U8g2.getDisplayHeight(u8g2), U8g2.
                getDrawColor(u8g2)));
        return u8g2;
    }

    /**
     * Sleep for desired milliseconds.
     *
     * @param milliseconds Milliseconds to sleep.
     */
    public void sleep(final long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Free u8g2_t structure from memory.
     *
     * @param u8g2 Pointer to u8g2_t structure.
     * @param i2c Use I2C display?
     */
    public void done(final long u8g2, final boolean i2c) {
        if (i2c) {
            logger.debug("Done I2C");
            U8g2.doneI2c();
            U8g2.donePins();
        } else {
            logger.debug("Done SPI");
            U8g2.doneSpi();
            U8g2.donePins();
        }
        U8g2.done(u8g2);
    }
}
