/*
 * U8g2 helper functions for Java wrapper.
 *
 * Copyright (c) Steven P. Goldsmith. All rights reserved.
 */

#ifdef __cplusplus
extern "C" {
#endif

#include <u8g2port.h>

u8g2_t *init_u8g2(void);
void done(u8g2_t *u8g2);
void set_pin(u8g2_t *u8g2, int pin, int value);

#ifdef __cplusplus
}
#endif