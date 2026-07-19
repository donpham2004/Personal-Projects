#ifndef MATERIAL_H
#define MATERIAL_H
#include <glad/glad.h>
#include <cglm/cglm.h>
#include "texture.h"
#define DIFFUSE_TEXTURE 0
#define SPECULAR_TEXTURE 1
struct material
{
    struct texture *diffuse;
    struct texture *specular;
    float shineness;
};

void material_init(struct material *material, float shineness);

void material_diffuse_init(struct material *material, const char *texture_path, int colorChannel);

void material_specular_init(struct material *material, const char *texture_path, int colorChannel);
void material_bind(struct material *material);

void material_destroy(struct material *material);
#endif