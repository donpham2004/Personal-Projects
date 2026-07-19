#ifndef _MESH_H_
#define _MESH_H_
#include <glad/glad.h>
#include <cglm/cglm.h>
#include "shader.h"
#include "array_buffer.h"
#include "vertex_buffer.h"
#include "element_buffer.h"
#include "texture.h"
#include "vertex.h"
#include "camera.h"
#include "light.h"
struct mesh
{
    struct array_buffer array_buffer;
    struct vertex_buffer vertex_buffer;
    struct element_buffer element_buffer;
};
void mesh_init(struct mesh *mesh, struct mesh_vertex *vertices, GLsizeiptr vsize, GLuint *indices, GLsizeiptr isize);
void mesh_bind(struct mesh *mesh);
void mesh_delete(struct mesh *mesh);
#endif