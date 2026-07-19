// Vertex buffer object, references memory buffer stored on gpu that holds vertex data
#ifndef _VERTEX_BUFFER_H_
#define _VERTEX_BUFFER_H_
#include <glad/glad.h>

struct vertex_buffer
{
    GLuint id;
};

void vertex_buffer_init(struct vertex_buffer *vertex_buffer, GLfloat *vertices, GLsizeiptr size);
void vertex_buffer_bind(struct vertex_buffer *vertex_buffer);
void vertex_buffer_unbind(void);
void vertex_buffer_destroy(struct vertex_buffer *vertex_buffer);
#endif