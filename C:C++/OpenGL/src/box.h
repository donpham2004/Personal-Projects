#ifndef BOX_H
#define BOX_H
#include <glad/glad.h>
#include <cglm/cglm.h>
#include "camera.h"
#include "light.h"
#include "mesh.h"
#include "bounding_box.h"
#include "material.h"

struct box
{
    struct shader shader;
    struct mesh mesh;
    struct material *material;
    struct bounding_box bound;
    vec3 linearVelocity;
    vec3 angularVelocity;
};

void box_init(struct box *box, vec3 pos, float width, float height, float depth);

void box_material_init(struct box *box, const char *diffusePath, const char *specularPath);
void box_render(struct box *box, struct camera *camera, struct light *lights, int numLights);

void box_delete(struct box *box);

#endif