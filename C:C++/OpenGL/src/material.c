#include <stdlib.h>
#include "material.h"

void material_init(struct material *material, float shineness)
{
    material->diffuse = material->specular = NULL;
    material->shineness = shineness;
}

void material_diffuse_init(struct material *material, const char *texture_path, int colorChannel)
{
    material->diffuse = malloc(sizeof(struct texture));
    texture_init(material->diffuse, texture_path, colorChannel);
}

void material_specular_init(struct material *material, const char *texture_path, int colorChannel)
{
    material->specular = malloc(sizeof(struct texture));
    texture_init(material->specular, texture_path, colorChannel);
}

void material_bind(struct material *material)
{
    if (material->diffuse != NULL)
        texture_bind(material->diffuse);
    if (material->specular != NULL)
        texture_bind(material->specular);
}

void material_destroy(struct material *material)
{
    if (material->diffuse != NULL)
        texture_delete(material->diffuse);
    if (material->specular != NULL)
        texture_delete(material->specular);
}