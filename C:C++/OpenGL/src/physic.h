#ifndef PHYSIC_H
#define PHYSIC_H
#include <cglm/cglm.h>
#include "bounding_box.h"
int collided(struct bounding_box *b1, struct bounding_box *b2);
void fix_collision(struct bounding_box *b1, vec3 vel1, struct bounding_box *b2);

#endif