// Element Array buffer, references memory buffer stored on gpu that holds vertex indices
#ifndef _ELEMENT_BUFFER_H_
#define _ELEMENT_BUFFER_H_

#include <glad/glad.h>

struct element_buffer
{
    GLuint id;
    GLsizeiptr size;
};

int element_buffer_init(struct element_buffer *element_buffer, GLuint *indices, GLsizeiptr size);
void element_buffer_bind(struct element_buffer *element_buffer);
void element_buffer_unbind(void);
void element_buffer_destroy(struct element_buffer *element_buffer);

#endif