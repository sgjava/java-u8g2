/*
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */
package com.codeferm.u8g2.demo;

import static com.codeferm.u8g2.Fonts.u8g2_font_t0_15b_mf;
import com.codeferm.u8g2.U8g2;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Test generic SSD1306 I2C. You can change setup for your device.
 *
 * @author Steven P. Goldsmith
 * @version 1.0.0
 * @since 1.0.0
 */
@Command(name = "text", mixinStandardHelpOptions = true, version = "text 1.0.0",
        description = "Text demo")
public class Text implements Callable<Integer> {

    /**
     * Logger.
     */
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Text.class);
    /**
     * Type option (true = I2C).
     */
    @Option(names = {"-t", "--type"}, description = "Defaults to I2C")
    private boolean type = true;
    /**
     * GPIO chip number.
     */
    @Option(names = {"-g", "--gpio"}, description = "GPIO chip number")
    private int gpio = 0x0;
    /**
     * I2C or SPI bus number.
     */
    @Option(names = {"-b", "--bus"}, description = "I2C or SPI bus number (0 default, for SPI 0x10 is /dev/spidev1.0)")
    private int bus = 0x0;
    /**
     * I2C address.
     */
    @Option(names = {"-a", "--address"}, description = "I2C address (0x3c default)")
    private int address = 0x3c;
    /**
     * DC pin for SPI.
     */
    @Option(names = {"-d", "--dc"}, description = "SPI DC pin (198 default)")
    private int dc = 198;
    /**
     * RESET pin for SPI.
     */
    @Option(names = {"-r", "--reset"}, description = "SPI RESET pin (199 default)")
    private int reset = 199;
    /**
     * Pointer to u8g2_t struct.
     */
    private long u8g2;
    /**
     * Display helper.
     */
    private Display display;
    /**
     * Display width.
     */
    private int width;
    /**
     * Display height.
     */
    private int height;

    /**
     * Display string for 5 seconds.
     *
     * @return Exit code.
     * @throws InterruptedException Possible exception.
     */
    @Override
    public Integer call() throws InterruptedException {
        var exitCode = 0;
        display = new Display();
        if (type) {
            // I2C
            u8g2 = display.init(gpio, bus, address);
        } else {
            //SPI
            u8g2 = display.init(gpio, bus, dc, reset);
        }
        U8g2.setPowerSave(u8g2, 0);
        U8g2.clearBuffer(u8g2);
        U8g2.setFont(u8g2, u8g2_font_t0_15b_mf);
        U8g2.drawStr(u8g2, 1, 18, "Java U8g2");
        U8g2.sendBuffer(u8g2);
        display.sleep(5000);
        U8g2.setPowerSave(u8g2, 1);
        display.done(u8g2, type);
        return exitCode;
    }

    /**
     * Main parsing, error handling and handling user requests for usage help or version help are done with one line of code.
     *
     * @param args Argument list.
     */
    public static void main(String... args) {
        System.exit(new CommandLine(new Text()).execute(args));
    }
}
