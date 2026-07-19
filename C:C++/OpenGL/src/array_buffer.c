#include "array_buffer.h"

int array_buffer_init(struct array_buffer *array_buffer)
{
    glGenVertexArrays(1, &array_buffer->id);
    array_buffer_bind(array_buffer);
}

void array_buffer_bind(struct array_buffer *array_buffer)
{
    glBindVertexArray(array_buffer->id);
}
void array_buffer_unbind(void)
{
    glBindVertexArray(0);
}
void array_buffer_destroy(struct array_buffer *array_buffer)
{
    glDeleteVertexArrays(1, &array_buffer->id);
}
void array_buffer_link_attrb(struct vertex_buffer *vertex_buffer, GLuint layout, GLuint numComponents, GLenum type, GLsizeiptr stride, void *offset)
{
    vertex_buffer_bind(vertex_buffer);
    glVertexAttribPointer(layout, numComponents, type, GL_FALSE, stride, offset);
    glEnableVertexAttribArray(layout);
    vertex_buffer_unbind();
}