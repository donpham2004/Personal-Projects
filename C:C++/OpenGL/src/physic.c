#include "physic.h"
#include <limits.h>

int check_collide(struct bounding_box *b1, struct bounding_box *b2)
{
    for (int i = 0; i < 3; i++)
    {
        if (!(b2->pos[i] + b2->dim[i] / 2 > b1->pos[i] - b1->dim[i] / 2 && b1->pos[i] + b1->dim[i] / 2 > b2->pos[i] - b2->dim[i] / 2))
            return 0;
    }
    return 1;
}

void fix_collision(struct bounding_box *b1, vec3 vel, struct bounding_box *b2)
{
    vec3 oldPos;
    glm_vec3_copy(b1->pos, oldPos);
    for (int i = 0; i < 3; i++)
    {
        float dPos = b1->pos[i] - b2->pos[i];
        float oldPos = b1->pos[i];
        b1->pos[i] += vel[i];
        if (dPos * vel[i] < 0)
        {

            float border = b2->pos[i];
            if (vel[i] < 0)
                border += (b2->dim[i] + b1->dim[i]) / 2;
            else
                border -= (b2->dim[i] + b1->dim[i]) / 2;
            if (check_collide(b1, b2))
                b1->pos[i] = border;
        }
    }
    glm_vec3_sub(b1->pos, oldPos, vel);
    glm_vec3_copy(oldPos, b1->pos);
}
