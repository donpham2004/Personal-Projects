#include "mesh.h"
#include "uniform.h"
#include <stddef.h>

void mesh_init(struct mesh *mesh, struct mesh_vertex *vertices, GLsizeiptr vsize, GLuint *indices, GLsizeiptr isize)
{
    array_buffer_init(&mesh->array_buffer);
    vertex_buffer_init(&mesh->vertex_buffer, (GLfloat *)vertices, vsize);
    element_buffer_init(&mesh->element_buffer, indices, isize);
    array_buffer_link_attrb(&mesh->vertex_buffer, 0, sizeof(vertices->coords) / sizeof(GLfloat), GL_FLOAT, sizeof(struct mesh_vertex), (void *)offsetof(struct mesh_vertex, coords));
    array_buffer_link_attrb(&mesh->vertex_buffer, 1, sizeof(vertices->colors) / sizeof(GLfloat), GL_FLOAT, sizeof(struct mesh_vertex), (void *)offsetof(struct mesh_vertex, colors));
    array_buffer_link_attrb(&mesh->vertex_buffer, 2, sizeof(vertices->texture) / sizeof(GLfloat), GL_FLOAT, sizeof(struct mesh_vertex), (void *)offsetof(struct mesh_vertex, texture));
    array_buffer_link_attrb(&mesh->vertex_buffer, 3, sizeof(vertices->normal) / sizeof(GLfloat), GL_FLOAT, sizeof(struct mesh_vertex), (void *)offsetof(struct mesh_vertex, normal));
    vertex_buffer_unbind();
    array_buffer_unbind();
    element_buffer_unbind();
}

void mesh_bind(struct mesh *mesh)
{
    array_buffer_bind(&mesh->array_buffer);
}

void mesh_delete(struct mesh *mesh)
{
    array_buffer_destroy(&mesh->array_buffer);
    vertex_buffer_destroy(&mesh->vertex_buffer);
    element_buffer_destroy(&mesh->element_buffer);
}