/*
 * U8g2 helper functions for Java wrapper.
 *
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */

#include "javau8g2.h"

/*
 * Return pointer to u8g2_t struct.
 */
u8g2_t *init_u8g2(void) {
    // Dynamically allocate u8g2_t struct
    u8g2_t * u8g2 = (u8g2_t *) malloc(sizeof(u8g2_t));
    return u8g2;
}

/*
 * Free memory.
 */
void done(u8g2_t *u8g2) {
    free(u8g2);
}

/*
 * u8x8_SetPin wrapper.
 */
void set_pin(u8g2_t *u8g2, int pin, int value) {
    u8x8_SetPin(u8g2_GetU8x8(u8g2), pin, value);
}
