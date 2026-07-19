#ifndef _TEXTURE_H_
#define _TEXTURE_H_
#include <glad/glad.h>

struct texture
{
    GLuint id;
    int slot;
    int width;
    int height;
    int numColCh;
};

int texture_init(struct texture *texture, const char *file_name, int colorFormat);
void texture_bind(struct texture *texture);
void texture_unbind(void);
void texture_delete(struct texture *texture);

#endif