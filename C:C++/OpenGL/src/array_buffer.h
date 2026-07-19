// Vertex Array Object, references list of buffers such as Vertex attributes that points to a VBO and EBO's
#ifndef _ARRAY_BUFFER_H_
#define _ARRAY_BUFFER_H_
#include <glad/glad.h>
#include "vertex_buffer.h"

struct array_buffer
{
    GLuint id;
};

int array_buffer_init(struct array_buffer *array_buffer);
void array_buffer_bind(struct array_buffer *array_buffer);
void array_buffer_unbind(void);
void array_buffer_destroy(struct array_buffer *array_buffer);
void array_buffer_link_attrb(struct vertex_buffer *vertex_buffer, GLuint layout, GLuint numComponents, GLenum type, GLsizeiptr stride, void* offset);

#endif