/*
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */
package com.codeferm.u8g2.demo;

import com.codeferm.u8g2.Common;
import static com.codeferm.u8g2.Fonts.u8g2_font_10x20_tr;
import com.codeferm.u8g2.U8g2;
import java.util.Random;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Graphics demo. Change device in Display class.
 *
 * @author Steven P. Goldsmith
 * @version 1.0.0
 * @since 1.0.0
 */
@Command(name = "graphics", mixinStandardHelpOptions = true, version = "graphics 1.0.0",
        description = "Graphics demo")
public class Graphics implements Callable<Integer> {

    /**
     * Logger.
     */
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Graphics.class);
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
     * XBM Java logo.
     */
    private final byte[] logo = {
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x10,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x30, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x30, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x38, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x0f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x07,
        (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0xe3, (byte) 0x01,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0xe0, (byte) 0x79, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0xf0, (byte) 0x3c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x78, (byte) 0x1e, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x3c, (byte) 0x0f, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x1c, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c,
        (byte) 0x0f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c, (byte) 0x0f,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x38, (byte) 0x1f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x38, (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x70, (byte) 0x3e,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x3c, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0x1c, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x84, (byte) 0x0c, (byte) 0x7c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc0,
        (byte) 0x03, (byte) 0x04, (byte) 0xf8, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xe0, (byte) 0x01,
        (byte) 0xfe, (byte) 0xc1, (byte) 0x01,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xe0, (byte) 0xff, (byte) 0x3f, (byte) 0xc0, (byte) 0x01, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0xff, (byte) 0x00, (byte) 0xc0, (byte) 0x01, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x03,
        (byte) 0x00, (byte) 0xe0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x01, (byte) 0x60,
        (byte) 0x70, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0xff, (byte) 0xff, (byte) 0x38,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x0e, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0xfc, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x02, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0x0c, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x30,
        (byte) 0xfc, (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x3e, (byte) 0x00,
        (byte) 0x00, (byte) 0x60, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x1f, (byte) 0x00, (byte) 0x00,
        (byte) 0x3c, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x7f, (byte) 0x00, (byte) 0xe0, (byte) 0x1f, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x80, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0xfe, (byte) 0xff, (byte) 0xcf,
        (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xe0, (byte) 0x01, (byte) 0xfe, (byte) 0x07,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0xff, (byte) 0x7f, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0xfe, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x98, (byte) 0x9f, (byte) 0x31, (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x18, (byte) 0x98,
        (byte) 0x31, (byte) 0x30, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x98, (byte) 0x31,
        (byte) 0x60, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x1c, (byte) 0x33, (byte) 0x78,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x98, (byte) 0x1f, (byte) 0x1b, (byte) 0x7f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0xd8, (byte) 0x18, (byte) 0x1b, (byte) 0x63, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xd8,
        (byte) 0x18, (byte) 0x9f, (byte) 0x71,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xd8, (byte) 0x1d, (byte) 0x8e, (byte) 0x7b, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x98, (byte) 0x17, (byte) 0x0e, (byte) 0x7f, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x1c,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
    };

    /**
     * Show text with delay. String is wrapped if too long for one line.
     *
     * @param text Text to show.
     */
    public void showText(final String text) {
        logger.debug(text);
        U8g2.clearBuffer(u8g2);
        // Does string fit display width?
        if (U8g2.getStrWidth(u8g2, text) < width) {
            U8g2.drawStr(u8g2, 1, 18, text);
        } else {
            // String exceeds width, so let's wrap
            var y = 18;
            var pos = 0;
            var str = "";
            while (y < height && pos < text.length()) {
                if (U8g2.getStrWidth(u8g2, str) < width) {
                    str += text.charAt(pos++);
                } else {
                    U8g2.drawStr(u8g2, 1, y, str);
                    str = "";
                    y += 18;
                }
            }
            // Draw last line
            if (!str.isEmpty()) {
                U8g2.drawStr(u8g2, 1, y, str);
            }
        }
        U8g2.sendBuffer(u8g2);
        U8g2.clearBuffer(u8g2);
        display.sleep(5000);
    }

    /**
     * Line examples.
     */
    public void lines() {
        showText("drawHLine");
        for (int y = 0; y < height; y += 4) {
            U8g2.drawHLine(u8g2, y, y, width - (y * 2));
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
        showText("drawVLine");
        for (int x = 0; x < width; x += 4) {
            U8g2.drawVLine(u8g2, x, 0, height - (x / 2));
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
        showText("drawLine");
        for (int y = 0; y < height; y += 4) {
            U8g2.drawLine(u8g2, 0, 0, width, y);
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
    }

    /**
     * Ellipse and circle examples.
     */
    public void ellipses() {
        showText("drawCircle");
        for (int r = 4; r < height / 2; r += 4) {
            U8g2.drawCircle(u8g2, width / 2, height / 2, r, U8g2.U8G2_DRAW_ALL);
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
        showText("drawDisc");
        for (int r = 4; r < height; r += 4) {
            U8g2.drawDisc(u8g2, width / 2, height / 2, r / 2, U8g2.U8G2_DRAW_ALL);
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
        showText("drawEllipse");
        for (int r = 4; r < height / 2; r += 4) {
            U8g2.drawEllipse(u8g2, width / 2, height / 2, r * 2, r, U8g2.U8G2_DRAW_ALL);
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
        showText("drawFilledEllipse");
        for (int r = 4; r < height; r += 4) {
            U8g2.drawFilledEllipse(u8g2, width / 2, height / 2, r, r / 2, U8g2.U8G2_DRAW_ALL);
            U8g2.sendBuffer(u8g2);
        }
        display.sleep(3000);
    }

    /**
     * Pixel example.
     */
    public void pixels() {
        final var random = new Random();
        showText("drawPixel");
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                U8g2.drawPixel(u8g2, random.nextInt(width), random.nextInt(height));
            }
            U8g2.sendBuffer(u8g2);
            U8g2.clearBuffer(u8g2);
        }
    }

    /**
     * Draw XBM image.
     */
    public void xbm() {
        showText("drawXBM");
        logger.debug(String.format("XBM length %d", logo.length));
        // Allocate native memory
        final var image = Common.malloc(logo.length);
        // Move Java byte array to native memory
        Common.memMove(image, logo, logo.length);
        U8g2.drawXBM(u8g2, 0, 0, 64, 64, image);
        U8g2.sendBuffer(u8g2);
        // Free native memory
        Common.free(image);
        display.sleep(3000);
    }

    /**
     * Run graphics routines.
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
        width = U8g2.getDisplayWidth(u8g2);
        height = U8g2.getDisplayHeight(u8g2);
        U8g2.setPowerSave(u8g2, 0);
        U8g2.setFont(u8g2, u8g2_font_10x20_tr);
        xbm();
        lines();
        ellipses();
        pixels();
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
        System.exit(new CommandLine(new Graphics()).execute(args));
    }
}