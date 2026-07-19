#ifndef PORTAL_H
#define PORTAL_H
#include <glad/glad.h>
#include <cglm/cglm.h>
#include "camera.h"
#include "shader.h"
#include "mesh.h"
struct portal_end
{
    struct shader shader;
    struct mesh mesh;
    mat3 orientation;
    vec3 pos;
};

struct portal
{
    struct portal_end ends[2];
};

void portal_end_init(struct portal_end *end, vec3 pos, vec3 direction, int width, int height);

void portal_render(struct portal *portal, struct camera *camera);
#endif