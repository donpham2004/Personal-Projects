#include "element_buffer.h"

int element_buffer_init(struct element_buffer *element_buffer, GLuint *indices, GLsizeiptr size)
{
    element_buffer->size = size;
    glGenBuffers(1, &element_buffer->id);
    element_buffer_bind(element_buffer);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, indices, GL_STATIC_DRAW);
}
void element_buffer_bind(struct element_buffer *element_buffer)
{
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, element_buffer->id);
}
void element_buffer_unbind(void)
{
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}
void element_buffer_destroy(struct element_buffer *element_buffer)
{
    glDeleteBuffers(1, &element_buffer->id);
}
