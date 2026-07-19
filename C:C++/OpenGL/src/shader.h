#ifndef _SHADER_H_
#define _SHADER_H_
#include <glad/glad.h>

struct shader
{
    GLuint id;
};

int shader_init(struct shader *shader, const char *vertexFile, const char *fragmentFile);
void shader_use(struct shader *shader);
void shader_destroy(struct shader *shader);

#endif