#include "shader.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <string.h>
#include <stdio.h>

static void compileErrors(unsigned int shader, const char *type)
{
    GLint hasCompiled;
    char infoLog[1024];
    if (!(strcmp(type, "PROGRAM") == 0))
    {
        glGetShaderiv(shader, GL_COMPILE_STATUS, &hasCompiled);
        if (hasCompiled == GL_FALSE)
        {
            glGetShaderInfoLog(shader, 1024, NULL, infoLog);

            fprintf(stderr, "Shader compilation error for: %s\n", type);
            fprintf(stderr, "%s", infoLog);
        }
    }
    else
    {
        glGetProgramiv(shader, GL_LINK_STATUS, &hasCompiled);
        if (hasCompiled == GL_FALSE)
        {
            glGetProgramInfoLog(shader, 1024, NULL, infoLog);
            fprintf(stderr, "Shader linking error for: %s\n", type);
            fprintf(stderr, "%s", infoLog);
        }
    }
}

static char *get_file_content(const char *filename)
{
    struct stat stbuf;
    char *ptr = NULL;
    int fd;
    int bytesRead;
    fd = open(filename, O_RDONLY);

    if (fd == -1)
        return NULL;

    if (fstat(fd, &stbuf) == -1)
        goto end;

    ptr = malloc(stbuf.st_size + 1);
    if (ptr == NULL)
        goto end;

    if ((bytesRead = read(fd, ptr, stbuf.st_size)) != stbuf.st_size)
    {
        free(ptr);
        ptr = NULL;
        goto end;
    }
    ptr[stbuf.st_size] = '\0';
end:
    close(fd);
    return ptr;
}

int shader_init(struct shader *shader, const char *vertexFile, const char *fragmentFile)
{
    char *vertexContent;

    char *fragmentContent;
    vertexContent = get_file_content(vertexFile);
    if (vertexContent == NULL)
    {
        return -1;
    }

    fragmentContent = get_file_content(fragmentFile);

    if (fragmentContent == NULL)
    {
        free(vertexContent);
        return -1;
    }
    // Create shaders, compile and add to shader program
    GLuint vertexShader = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShader, 1, (const char **)&vertexContent, NULL);
    glCompileShader(vertexShader);
    compileErrors(vertexShader, "VERTEX");
    GLuint fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShader, 1, (const char **)&fragmentContent, NULL);
    glCompileShader(fragmentShader);
    compileErrors(fragmentShader, "FRAGMENT");

    shader->id = glCreateProgram();
    glAttachShader(shader->id, vertexShader);
    glAttachShader(shader->id, fragmentShader);

    glLinkProgram(shader->id);
    compileErrors(shader->id, "PROGRAM");

    // No need since program has shader built into already
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);

    free(vertexContent);
    free(fragmentContent);
    return 0;
}

void shader_use(struct shader *shader)
{
    glUseProgram(shader->id);
}

void shader_destroy(struct shader *shader)
{
    glDeleteProgram(shader->id);
}
