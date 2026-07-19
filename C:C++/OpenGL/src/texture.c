#include "texture.h"
#define STB_IMAGE_IMPLEMENTATION
#include <stb/stb_image.h>
#include <stdio.h>

static int slot = 0;
int texture_init(struct texture *texture, const char *file_name, int colorFormat)
{
    unsigned char *bytes;
    stbi_set_flip_vertically_on_load(1);
    bytes = stbi_load(file_name, &texture->width, &texture->height, &texture->numColCh, 0);
    if (bytes == NULL)
        return -1;
    texture->slot = slot++;
    glGenTextures(1, &texture->id);
    texture_bind(texture);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexImage2D(GL_TEXTURE_2D, 0, colorFormat, texture->width, texture->height, 0, colorFormat, GL_UNSIGNED_BYTE, bytes);
    glGenerateMipmap(GL_TEXTURE_2D);
    stbi_image_free(bytes);
    texture_unbind();
}
void texture_bind(struct texture *texture)
{
    glActiveTexture(GL_TEXTURE0 + texture->slot);
    glBindTexture(GL_TEXTURE_2D, texture->id);
}
void texture_unbind(void)
{
    glBindTexture(GL_TEXTURE_2D, 0);
}
void texture_delete(struct texture *texture)
{
    glDeleteTextures(1, &texture->id);
}