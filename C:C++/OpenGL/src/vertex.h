#ifndef _VERTEX_H_
#define _VERTEX_H_
#include <glad/glad.h>

struct mesh_vertex
{
    GLfloat coords[3];
    GLfloat colors[3];
    GLfloat texture[2];
    GLfloat normal[3];
    
};

struct light_vertex
{
    GLfloat coords[3];
};

#endif