#include "vertex_buffer.h"

void vertex_buffer_init(struct vertex_buffer *vertex_buffer, GLfloat *vertices, GLsizeiptr size)
{
    glGenBuffers(1, &vertex_buffer->id);
    vertex_buffer_bind(vertex_buffer);
    glBufferData(GL_ARRAY_BUFFER, size, vertices, GL_STATIC_DRAW);
}
void vertex_buffer_bind(struct vertex_buffer *vertex_buffer)
{
    glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer->id);
}
void vertex_buffer_unbind(void)
{
    glBindBuffer(GL_ARRAY_BUFFER, 0);
}
void vertex_buffer_destroy(struct vertex_buffer *vertex_buffer)
{
    glDeleteBuffers(1, &vertex_buffer->id);
}
