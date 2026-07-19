#ifndef _LIGHT_H_
#define _LIGHT_H_
#include <glad/glad.h>
#include <cglm/cglm.h>
#include "mesh.h"
#include "shader.h"
#include "vertex.h"
#include "camera.h"
struct light
{
    struct mesh *mesh;
    struct shader *shader;
    vec4 color;
    vec3 pos;
};

void light_init(struct light *light, vec4 color, vec3 pos);
void light_vertex_init(struct light *light, struct light_vertex *vertices, GLsizeiptr vsize, GLuint *indices, GLsizeiptr isize);
void light_shader_init(struct light *light);
void light_render(struct light *light, struct camera *camera);
void light_delete(struct light *light);
#endif